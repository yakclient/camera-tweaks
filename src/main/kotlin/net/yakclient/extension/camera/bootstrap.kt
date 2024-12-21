package net.yakclient.extension.camera

val bootstrapCallbacks = ArrayList<() -> Unit>()

fun onBootstrap(callback: () -> Unit) {
    bootstrapCallbacks.add(callback)
}