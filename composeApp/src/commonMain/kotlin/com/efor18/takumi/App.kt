package com.efor18.takumi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
import com.efor18.takumi.designsystem.components.platformSystemBarsPadding
import com.efor18.takumi.di.WithKoin
import com.efor18.takumi.navigation.AppNavGraph

@Composable
@Preview
fun App() {
    // Initialize Coil ImageLoader
    setSingletonImageLoaderFactory { context ->
        getAsyncImageLoader(context)
    }

    WithKoin {
        MaterialTheme {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .platformSystemBarsPadding()
            ) {
                AppNavGraph()
            }
        }
    }
}

fun getAsyncImageLoader(context: PlatformContext) =
    ImageLoader.Builder(context)
        .crossfade(true)
        .logger(DebugLogger())
        .build()
