package org.berendeev.android

import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property

interface ThemeExtension {
    val generatedCodeFolder: DirectoryProperty
    val packageName: Property<String>
}
