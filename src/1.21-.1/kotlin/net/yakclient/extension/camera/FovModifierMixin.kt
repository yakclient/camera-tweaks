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

@Mixin(AbstractClientPlayer::class)
abstract class FovModifierMixin(
    level: Level,
    pos: BlockPos,
    f: Float,
    profile: GameProfile
) : Player(
    level, pos, f, profile
) {
    @SourceInjection(
        point = "after-begin",
        methodTo = "getFieldOfViewModifier()F"
    )
    fun modifyFov(continuation: InjectionContinuation): InjectionContinuation.Result {
        var fieldOfView = 1.0F

        if (this.abilities.flying) {
            fieldOfView *= 1.1F
        }

        if (zoomKey.isDown) {
            fieldOfView = 0.1f
        }

        val currentItem = this.useItem
        if (this.isUsingItem) {
            if (currentItem.`is`(Items.BOW)) {
                val ticksUsingItem = this.getTicksUsingItem()
                var chargeFactor = ticksUsingItem.toFloat() / 20.0F
                chargeFactor = if (chargeFactor > 1.0F) {
                    1.0F
                } else {
                    chargeFactor * chargeFactor
                }

                fieldOfView *= 1.0F - chargeFactor * 0.15F
            } else if (Minecraft.getInstance().options.cameraType.isFirstPerson && this.isScoping()) {
                return continuation.returnEarly(0.1F)
            }
        }

        val fovEffectScale = Minecraft.getInstance().options.fovEffectScale().get().toFloat()
        return continuation.returnEarly(Mth.lerp(fovEffectScale, 1.0F, fieldOfView))

    }


}