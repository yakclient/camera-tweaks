package net.yakclient.extension.camera

import com.mojang.authlib.GameProfile
import dev.extframework.mixin.api.*
import net.minecraft.client.entity.AbstractClientPlayer
import net.minecraft.client.settings.KeyBinding
import net.minecraft.entity.ai.attributes.IAttributeInstance
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Bootstrap
import net.minecraft.world.World

val zoomKey = KeyBinding("Zoom", 46, "key.categories.misc")

@Mixin(AbstractClientPlayer::class)
abstract class FovModifierMixin(p0: World, p1: GameProfile) : EntityPlayer(p0, p1) {
    @InjectCode(
        "getFovModifier",
        point = Select(
            invoke = Invoke(
                IAttributeInstance::class,
                "getAttributeValue()D"
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
        "getFovModifier",
        point = Select(
            InjectionBoundary.TAIL
        ),
        locals = [0]
    )
    fun getZoomModifier(
        modifier: Captured<Float>,
        flow: MixinFlow
    ) : MixinFlow.Result<*> {
        val zoom = if (zoomKey.isKeyDown) 0.1f else 1f

        return flow.yield(modifier.get() * zoom)
    }
}