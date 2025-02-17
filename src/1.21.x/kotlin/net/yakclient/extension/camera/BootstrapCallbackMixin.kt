package net.yakclient.extension.camera

import dev.extframework.mixin.api.InjectCode
import dev.extframework.mixin.api.Mixin
import dev.extframework.mixin.api.MixinFlow
import net.minecraft.client.Minecraft

@Mixin(Minecraft::class)
abstract class BootstrapCallbackMixin {
    @InjectCode
    fun run(flow: MixinFlow) {
        bootstrapCallbacks.forEach {
            it()
        }
    }
}