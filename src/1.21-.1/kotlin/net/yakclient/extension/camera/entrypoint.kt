package net.yakclient.extension.camera

import dev.extframework.core.capability.Capability0
import dev.extframework.core.entrypoint.Entrypoint
import net.minecraft.client.Minecraft
import kotlin.collections.plus

class E1_21: Entrypoint() {
    override fun init() {
        registerKeyBinds += Capability0 {
            Minecraft.getInstance().options.keyMappings = Minecraft.getInstance().options.keyMappings + zoomKey
        }
    }
}