package com.efor18.takumi.designsystem.components

import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun Modifier.platformSystemBarsPadding(): Modifier {
    return this.safeContentPadding()
}
