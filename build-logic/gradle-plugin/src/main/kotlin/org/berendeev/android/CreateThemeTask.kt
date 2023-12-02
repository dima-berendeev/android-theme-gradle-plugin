package org.berendeev.android

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction

abstract class CreateThemeTask : DefaultTask() {
    @get:InputDirectory
    abstract val sourcesFolder: DirectoryProperty

    @get:Input
    abstract val packageName: Property<String>

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
                
                val DarkColorScheme = darkColorScheme(
                    primary = Purple80,
                    secondary = PurpleGrey80,
                    tertiary = Pink80
                )
    
                val LightColorScheme = lightColorScheme(
                    primary = Purple40,
                    secondary = PurpleGrey40,
                    tertiary = Pink40
                )
                """.trimIndent()
            )
        }
    }
}