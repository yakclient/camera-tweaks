import dev.extframework.gradle.common.mixin
import dev.extframework.gradle.deobf.MinecraftMappings
import dev.extframework.gradle.extframework
import dev.extframework.gradle.publish.ExtensionPublication

plugins {
    kotlin("jvm") version "2.0.21"
    id("dev.extframework.mc") version "1.2.32"
    id("dev.extframework.common") version "1.0.50"
}

group = "net.yakclient.extension"
version = "1.0.3-BETA"

tasks.wrapper {
    gradleVersion = "8.6-rc-1"
}

tasks.launch {
    targetNamespace.set(MinecraftMappings.mojang.obfuscatedNamespace)
    javaLauncher.set(javaToolchains.launcherFor {
        languageVersion.set(JavaLanguageVersion.of(21))
    })
    mcVersion.set("1.21.4")
}

dependencies {
    implementation(kotlin("stdlib"))
}

repositories {
    mavenLocal()
    mavenCentral()
    extframework()
    maven {
        url = uri("https://repo.extframework.dev/registry")
    }
}

extension {
    model {
        name = "camera-tweaks"
    }
    extensions {
        require("dev.extframework.extension:mcp-mappings:1.0.4-BETA")
        require("dev.extframework.extension:access-tweaks:1.0.1-BETA")
    }
    partitions {
        main {
            extensionClass = "net.yakclient.extension.camera.CameraExtension"
            dependencies {
                implementation("dev.extframework.core:entrypoint:1.0-BETA")
                implementation("dev.extframework.core:capability:1.0-BETA")
                implementation("dev.extframework.core:minecraft-api:1.0-BETA")
            }
        }
        version("legacy") {
            mappings = MinecraftMappings.mcpLegacy
            entrypoint = "net.yakclient.extension.camera.LegacyEntrypoint"
            dependencies {
                minecraft("1.8.9")
                implementation("dev.extframework.core:entrypoint:1.0-BETA")
                implementation("dev.extframework.core:capability:1.0-BETA")
                implementation("dev.extframework.core:minecraft-api:1.0-BETA")
                mixin()
            }
            supportVersions("1.8.9")
        }
        version("1.21.x") {
            mappings = MinecraftMappings.mojang
            dependencies {
                minecraft("1.21")
                implementation("dev.extframework.core:entrypoint:1.0-BETA")
                implementation("dev.extframework.core:capability:1.0-BETA")
                implementation("dev.extframework.core:minecraft-api:1.0-BETA")
                mixin()
            }
            supportVersions("1.21", "1.21.1","1.21.2", "1.21.3","1.21.4")
        }
        version("1.21-.1") {
            entrypoint = "net.yakclient.extension.camera.E1_21"
            mappings = MinecraftMappings.mojang
            dependencies {
                minecraft("1.21")
                implementation("dev.extframework.core:entrypoint:1.0-BETA")
                implementation("dev.extframework.core:capability:1.0-BETA")
                implementation("dev.extframework.core:minecraft-api:1.0-BETA")
                mixin()
            }
            supportVersions("1.21", "1.21.1",)
        }
        version("1.21.2-4") {
            mappings = MinecraftMappings.mojang
            entrypoint = "net.yakclient.extension.camera.E1_21_2"
            dependencies {
                minecraft("1.21.2")
                implementation("dev.extframework.core:entrypoint:1.0-BETA")
                implementation("dev.extframework.core:capability:1.0-BETA")
                implementation("dev.extframework.core:minecraft-api:1.0-BETA")
                mixin()
            }
            supportVersions("1.21.2", "1.21.3","1.21.4")
        }
    }

    metadata {
        name = "Camera extension"
        description = "An extension that modifies the camera."
        developers.add("yakclient")
    }
}

publishing {
    publications {
        create("prod", ExtensionPublication::class.java)
    }
    repositories {
        maven {
            url = uri("https://repo.extframework.dev")
            credentials {
                password = properties["creds.ext.key"] as? String
            }
        }
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}