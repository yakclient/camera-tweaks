@file:ImplementFeatures

package net.yakclient.extension.camera

import dev.extframework.core.api.feature.Feature
import dev.extframework.core.api.feature.ImplementFeatures
import net.minecraft.client.Minecraft
import net.minecraft.client.settings.KeyBinding

@Feature
fun registerKeyBinds() {
    Minecraft.getMinecraft().gameSettings.keyBindings = Minecraft.getMinecraft().gameSettings.keyBindings + zoomKey
}