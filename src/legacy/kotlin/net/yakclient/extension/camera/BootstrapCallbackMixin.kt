package net.yakclient.extension.camera

import dev.extframework.mixin.api.InjectCode
import dev.extframework.mixin.api.InjectionBoundary.TAIL
import dev.extframework.mixin.api.Mixin
import dev.extframework.mixin.api.Select
import net.minecraft.client.Minecraft

@Mixin(Minecraft::class)
abstract class BootstrapCallbackMixin {
    @InjectCode(
        "startGame",
        point = Select(
            TAIL
        )
    )
    fun inject() {
        bootstrapCallbacks.forEach { it() }
    }
}