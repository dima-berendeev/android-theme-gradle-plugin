package org.berendeev.android

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction

abstract class CreateThemeTask : DefaultTask() {
    @get:InputDirectory
    abstract val sourcesFolder: DirectoryProperty

    @get:Input
    abstract val packageName: Property<String>

    @get:Input
    abstract val colorSchemas: ListProperty<ColorScheme>

    @TaskAction
    fun action() {
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

            colorSchemas.get().forEach { colorSchema ->
                val scheme = if (colorSchema.lightScheme.get()) {
                    "lightColorScheme"
                } else {
                    "darkColorScheme"
                }
                it.write(
                    """
                val ${colorSchema.name} = $scheme(
                    primary = Color(0x${colorSchema.primary.get().toString(16)}),
                    secondary = PurpleGrey40,
                    tertiary = Pink40
                )
                """.trimIndent() + "\n\n"
                )
            }
        }
    }
}
