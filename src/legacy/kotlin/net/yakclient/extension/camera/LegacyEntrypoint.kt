package net.yakclient.extension.camera

import dev.extframework.core.capability.Capability0
import dev.extframework.core.entrypoint.Entrypoint
import net.minecraft.client.Minecraft

class LegacyEntrypoint: Entrypoint() {
    override fun init() {
        registerKeyBinds += Capability0 {
            Minecraft.getMinecraft().gameSettings.keyBindings =
                (Minecraft.getMinecraft().gameSettings.keyBindings) + zoomKey
        }
    }
}