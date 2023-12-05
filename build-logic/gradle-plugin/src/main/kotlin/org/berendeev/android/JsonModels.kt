package org.berendeev.android

import kotlinx.serialization.Serializable

@Serializable
data class ThemeDataJsonModel(
    val colors: Colors,
    val isDark: Boolean
) {
    @Serializable
    data class Colors(
        val primary: String,
        val secondary: String
    )
}
