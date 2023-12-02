package org.berendeev.android

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create

class ThemePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val extension = target.extensions.create<ThemeExtension>("theme")
        target.tasks.create<CreateThemeTask>("createTheme") {
            sourcesFolder.set(extension.generatedCodeFolder)
            packageName.set(extension.packageName)
        }
    }
}
