package org.berendeev.android

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class CreateThemeTask : DefaultTask() {
    @get:Input
    abstract val packageName: Property<String>

    @get:InputFile
    abstract val jsonFile: RegularFileProperty

    @get:Input
    abstract val themeName: Property<String>

    @get:OutputDirectory
    abstract val outputDirectory: DirectoryProperty

    init {
        group = "theme"
    }

    @TaskAction
    fun action() {
        val decodedTheme = fetchTheme(jsonFile.get().asFile)
        val file = outputDirectory.file(
            "${
                packageName.get().replace(".", "/")
            }/Generated${themeName.get()}.kt"
        )
            .get()
            .asFile
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

            val scheme = if (decodedTheme.isDark) {
                "darkColorScheme"
            } else {
                "lightColorScheme"
            }
            it.write(
                """
                val ${themeName.get()} = $scheme(
                    primary = Color(0x${decodedTheme.colors.primary}),
                    secondary = Color(0x${decodedTheme.colors.secondary}),
                    tertiary = Pink40
                )
                """.trimIndent() + "\n\n"
            )
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
