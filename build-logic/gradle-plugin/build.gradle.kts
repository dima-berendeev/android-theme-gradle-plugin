plugins {
    `kotlin-dsl`
    kotlin("jvm") version "1.8.20"
}


gradlePlugin {
    plugins {
        create("themePlugin") {
            id = "org.berendeev.android.theme-plugin"
            implementationClass = "org.berendeev.android.ThemePlugin"
        }
    }
}
