package net.yakclient.extension.camera

import com.mojang.authlib.GameProfile
import dev.extframework.core.api.mixin.InjectionContinuation
import dev.extframework.core.api.mixin.Mixin
import dev.extframework.core.api.mixin.SourceInjection
import net.minecraft.client.Minecraft
import net.minecraft.client.entity.AbstractClientPlayer
import net.minecraft.client.renderer.EntityRenderer
import net.minecraft.client.resources.IResourceManager
import net.minecraft.client.settings.KeyBinding
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.world.World

val zoomKey = KeyBinding("Zoom", 46, "key.categories.misc")

@Mixin(AbstractClientPlayer::class)
abstract class FovModifierMixin(p0: World, p1: GameProfile) : EntityPlayer(p0, p1) {
    @SourceInjection(
        point = "after-begin",
        methodTo = "getFovModifier()F"
    )
    fun modifyFov(continuation: InjectionContinuation): InjectionContinuation.Result {
        var fov = 1.0f

        if (this.capabilities.walkSpeed == 0.0f || fov.isNaN() || fov.isInfinite()) {
            fov = 1.0f
        }

        if (zoomKey.isKeyDown) {
           fov = 0.1f
        }

        if (this.isUsingItem && this.itemInUse.item == Items.bow) {
            val var3 = this.itemInUseDuration
            var var4 = var3 / 20.0f
            var4 = if (var4 > 1.0f) {
                1.0f
            } else {
                var4 * var4
            }

            fov *= 1.0f - var4 * 0.15f
        }

        return continuation.returnEarly(fov)
    }
}