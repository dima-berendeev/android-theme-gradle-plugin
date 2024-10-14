package org.berendeev.android

import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.gradle.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.register

class ThemePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val extension = target.extensions.create<ThemeExtension>("theme")
        extension.themes.all {
            val theme = this

            val createTheme = target.tasks.register<CreateThemeTask>("createTheme${theme.name}") {
                packageName.set(extension.packageName)
                themeName.set(theme.name)
                jsonFile.set(theme.json)
                outputs.upToDateWhen { false }
            }

            target.plugins.withType(AppPlugin::class.java) {
                val androidComponents: ApplicationAndroidComponentsExtension =
                    target.extensions.getByType(ApplicationAndroidComponentsExtension::class.java)

                androidComponents.onVariants { variant ->
                    println("-------->${theme.name} ---> variant: $variant")
                    checkNotNull(variant.sources.java).addGeneratedSourceDirectory(
                        createTheme,
                        CreateThemeTask::outputDirectory
                    )
                }
            }

        }
    }
}
