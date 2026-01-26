package com.efor18.takumi.designsystem.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun Modifier.platformSystemBarsPadding(): Modifier {
    return this.windowInsetsPadding(WindowInsets.statusBars)
}
