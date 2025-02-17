package net.yakclient.extension.camera

import dev.extframework.core.capability.Capability0
import dev.extframework.core.capability.defining
import dev.extframework.core.minecraft.api.TargetCapabilities

val registerKeyBinds by TargetCapabilities.defining<Capability0<Unit>>()