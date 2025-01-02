@file:ImplementFeatures

package net.yakclient.extension.camera

import dev.extframework.core.api.feature.Feature
import dev.extframework.core.api.feature.ImplementFeatures
import net.minecraft.client.Minecraft
import net.minecraft.client.Options
import net.minecraft.server.Bootstrap

@Feature
fun registerKeyBinds() {
    Bootstrap.realStdoutPrintln("HERE")

    val cls = Options::class.java
    val field = cls.getField("keyMappings")
    field.isAccessible = true
    field.set(Minecraft.getInstance().options, Minecraft.getInstance().options.keyMappings + zoomKey)
}