package org.berendeev.android

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create

class ThemePlugin:Plugin<Project> {
    override fun apply(target: Project) {
        target.extensions.create<ThemeExtension>("theme")

    }
}

interface ThemeExtension{

}
