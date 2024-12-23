plugins {
    id("java")
    id("xyz.jpenilla.run-paper") version "2.3.1"
    id("com.gradleup.shadow") version "9.0.0-beta4"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven("https://repo.codemc.io/repository/maven-public/") {
        name = "Vault"
    }

    maven {
        name = "cm-repo"
        url = uri("https://ci.pluginwiki.us/plugin/repository/everything/")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
    compileOnly("net.milkbowl.vault:VaultAPI:1.7")
    implementation("com.github.thatsmusic99:ConfigurationMaster-API:v2.0.0-rc.2")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks {
    runServer {
        minecraftVersion("1.21.1")
    }
}