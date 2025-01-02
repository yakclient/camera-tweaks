package net.yakclient.extension.camera

import com.mojang.authlib.GameProfile
import dev.extframework.core.api.mixin.InjectionContinuation
import dev.extframework.core.api.mixin.Mixin
import dev.extframework.core.api.mixin.SourceInjection
import net.minecraft.client.Minecraft
import net.minecraft.client.player.AbstractClientPlayer
import net.minecraft.core.BlockPos
import net.minecraft.util.Mth
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Items
import net.minecraft.world.level.Level
import kotlin.math.min

@Mixin(AbstractClientPlayer::class)
abstract class FovModifierMixin(
    level: Level,
    pos: BlockPos,
    f: Float,
    profile: GameProfile
) : Player(
    level, pos, f, profile
)  {
    @SourceInjection(
        point = "after-begin",
        methodTo = "getFieldOfViewModifier(ZF)F"
    )
    fun modifyFov(isScoping: Boolean, fovEffectScale: Float, continuation: InjectionContinuation): InjectionContinuation.Result {
        var fieldOfViewModifier = 1.0F
        if (this.abilities.flying) {
            fieldOfViewModifier *= 1.1F
        }

        if (zoomKey.isDown) {
            fieldOfViewModifier = 0.1f
        }

        if (this.isUsingItem) {
            val currentItem = this.useItem
            if (currentItem.`is`(Items.BOW)) {
                val chargeProgress = min(this.ticksUsingItem.toFloat() / 20.0F, 1.0F)
                fieldOfViewModifier *= 1.0F - Mth.square(chargeProgress) * 0.15F
            } else if (isScoping && this.isScoping) {
                return continuation.returnEarly(0.1F)
            }
        }

        return continuation.returnEarly(Mth.lerp(fovEffectScale, 1.0F, fieldOfViewModifier))

    }
}