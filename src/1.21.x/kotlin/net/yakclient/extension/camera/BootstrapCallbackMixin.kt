package net.yakclient.extension.camera

import dev.extframework.core.api.mixin.InjectionContinuation
import dev.extframework.core.api.mixin.Mixin
import dev.extframework.core.api.mixin.SourceInjection
import net.minecraft.client.Minecraft
import net.minecraft.server.Bootstrap

@Mixin(Minecraft::class)
abstract class BootstrapCallbackMixin {
    @SourceInjection(point = "after-begin", methodTo = "run()V")
    fun startGame(continuation: InjectionContinuation): InjectionContinuation.Result {
        bootstrapCallbacks.forEach {
            it()
        }

        return continuation.resume()
    }
}