package net.yakclient.extension.camera

import com.mojang.authlib.GameProfile
import dev.extframework.mixin.api.Captured
import dev.extframework.mixin.api.InjectCode
import dev.extframework.mixin.api.InjectionBoundary
import dev.extframework.mixin.api.InjectionType
import dev.extframework.mixin.api.Invoke
import dev.extframework.mixin.api.Mixin
import dev.extframework.mixin.api.MixinFlow
import dev.extframework.mixin.api.Select
import dev.extframework.mixin.api.Stack
import net.minecraft.client.Minecraft
import net.minecraft.client.player.AbstractClientPlayer
import net.minecraft.core.BlockPos
import net.minecraft.util.Mth
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Items
import net.minecraft.world.level.Level

@Mixin(AbstractClientPlayer::class)
abstract class FovModifierMixin(
    level: Level,
    pos: BlockPos,
    f: Float,
    profile: GameProfile
) : Player(
    level, pos, f, profile
) {
    @InjectCode(
        "getFieldOfViewModifier",
        point = Select(
            invoke = Invoke(
                AbstractClientPlayer::class,
                "getAttributeValue(Lnet/minecraft/core/Holder;)D"
            )
        ),
        type = InjectionType.AFTER
    )
    fun walkModifier(
        stack: Stack,
    ) {
        stack.replaceLast(0.1)
    }

    @InjectCode(
        "getFieldOfViewModifier",
        point = Select(
            InjectionBoundary.TAIL
        ),
        locals = [0]
    )
    fun getZoomModifier(
        modifier: Captured<Float>,
        flow: MixinFlow
    ) : MixinFlow.Result<*> {
        val zoom = if (zoomKey.isDown) 0.1f else 1f

        return flow.yield(modifier.get() * zoom)
    }
}