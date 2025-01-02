import com.durganmcbroom.artifact.resolver.simple.maven.layout.mavenLocal
import dev.extframework.gradle.common.coreApi
import dev.extframework.gradle.deobf.MinecraftMappings
import dev.extframework.gradle.extframework
import dev.extframework.gradle.publish.ExtensionPublication

plugins {
    kotlin("jvm") version "2.0.21"
    id("dev.extframework.mc") version "1.2.29"
    id("dev.extframework.common") version "1.0.45"
}

group = "net.yakclient.extension"
version = "1.0.1-BETA"

tasks.wrapper {
    gradleVersion = "8.6-rc-1"
}

tasks.launch {
    targetNamespace.set(MinecraftMappings.mojang.deobfuscatedNamespace)
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
        require("dev.extframework.extension:mcp-mappings:1.0.2-BETA")
    }
    partitions {
        main {
            extensionClass = "net.yakclient.extension.camera.CameraExtension"
            dependencies {
                coreApi()
            }
        }
        version("legacy") {
            mappings = MinecraftMappings.mcpLegacy
            dependencies {
                minecraft("1.8.9")
                coreApi()
            }
            supportVersions("1.8.9")
        }
        version("1.21.x") {
            mappings = MinecraftMappings.mojang
            dependencies {
                minecraft("1.21")
                coreApi()
            }
            supportVersions("1.21", "1.21.1","1.21.2", "1.21.3","1.21.4")
        }
        version("1.21-.1") {
            mappings = MinecraftMappings.mojang
            dependencies {
                minecraft("1.21")
                coreApi()
            }
            supportVersions("1.21", "1.21.1",)
        }
        version("1.21.2-4") {
            mappings = MinecraftMappings.mojang
            dependencies {
                minecraft("1.21.2")
                coreApi()
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