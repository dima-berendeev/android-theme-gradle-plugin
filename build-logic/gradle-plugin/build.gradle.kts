plugins {
    `kotlin-dsl`
    kotlin("jvm") version "1.8.20"
    kotlin("plugin.serialization") version "1.9.10"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}


gradlePlugin {
    plugins {
        create("themePlugin") {
            id = "org.berendeev.android.theme-plugin"
            implementationClass = "org.berendeev.android.ThemePlugin"
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    implementation(libs.com.android.tools.build)
}
