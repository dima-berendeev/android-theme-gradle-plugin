package org.berendeev.android

import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property

interface ThemeExtension {
    val generatedCodeFolder: DirectoryProperty
    val packageName: Property<String>
    val colorSchemas: NamedDomainObjectContainer<ColorScheme>
}

interface ColorScheme {
    val name: String
    val lightScheme: Property<Boolean>
    val primary: Property<UInt>
}
