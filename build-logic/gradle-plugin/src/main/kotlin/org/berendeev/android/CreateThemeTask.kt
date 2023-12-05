package org.berendeev.android

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class CreateThemeTask : DefaultTask() {
    @get:InputDirectory
    abstract val sourcesFolder: DirectoryProperty

    @get:Input
    abstract val packageName: Property<String>

    @get:Input
    abstract val themes: ListProperty<Theme>


    @TaskAction
    fun action() {
        val decodedThemes: Map<String, ThemeDataJsonModel> = themes.get().map {
            val data = fetchTheme(it.json.get().asFile)
            it.name to data
        }.toMap()

        val file = sourcesFolder.file("${packageName.get().replace(".", "/")}/GeneratedColorScheme.kt").get().asFile
        file.ensureParentDirsCreated()
        file.outputStream().writer().use {
            it.write(
                """
                package ${packageName.get()}
                
                import androidx.compose.material3.darkColorScheme
                import androidx.compose.material3.lightColorScheme
                import androidx.compose.ui.graphics.Color
                """.trimIndent() + "\n\n"
            )

            decodedThemes.forEach { (themeName, themeModel) ->
                val scheme = if (themeModel.isDark) {
                    "darkColorScheme"
                } else {
                    "lightColorScheme"
                }
                it.write(
                    """
                val $themeName = $scheme(
                    primary = Color(0x${themeModel.colors.primary}),
                    secondary = Color(0x${themeModel.colors.secondary}),
                    tertiary = Pink40
                )
                """.trimIndent() + "\n\n"
                )
            }
        }
    }

    private fun fetchTheme(file: File): ThemeDataJsonModel {
        return json.decodeFromStream<ThemeDataJsonModel>(file.inputStream())
    }

    companion object {
        val json = Json {
            ignoreUnknownKeys = true
        }
    }
}
