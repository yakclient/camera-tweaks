package net.yakclient.extension.camera

import dev.extframework.core.api.mixin.InjectionContinuation
import dev.extframework.core.api.mixin.Mixin
import dev.extframework.core.api.mixin.SourceInjection
import net.minecraft.client.Minecraft

@Mixin(Minecraft::class)
abstract class BootstrapCallbackMixin {
    @SourceInjection(point = "before-end", methodTo = "startGame()V")
    fun startGame(continuation: InjectionContinuation) : InjectionContinuation.Result {
        bootstrapCallbacks.forEach { it() }
        return continuation.resume()
    }
}