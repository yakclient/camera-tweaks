package net.yakclient.extension.camera

import dev.extframework.core.api.Extension

class CameraExtension : Extension() {
    override fun init() {
        onBootstrap {
            registerKeyBinds()
        }
    }
}