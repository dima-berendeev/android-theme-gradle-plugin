package org.berendeev.android

import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property

interface ThemeExtension {
    val generatedCodeFolder: DirectoryProperty
    val packageName: Property<String>
    val themes: NamedDomainObjectContainer<Theme>
}

interface Theme {
    val name: String
    val json: RegularFileProperty
}
