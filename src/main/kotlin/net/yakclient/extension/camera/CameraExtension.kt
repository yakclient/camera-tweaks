package net.yakclient.extension.camera

import dev.extframework.core.entrypoint.Entrypoint

class CameraExtension : Entrypoint() {
    override fun init() {
        onBootstrap {
            registerKeyBinds.call()
        }
    }
}