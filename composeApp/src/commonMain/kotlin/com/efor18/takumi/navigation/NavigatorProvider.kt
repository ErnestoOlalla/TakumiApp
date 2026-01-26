package com.efor18.takumi.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import com.efor18.takumi.common.navigation.Navigator

/**
 * CompositionLocal for providing Navigator to the component tree
 */
val LocalNavigator = compositionLocalOf<Navigator> {
    error("No Navigator provided") 
}

/**
 * Simple holder for Navigator instance that can be shared across the app
 */
object NavigatorHolder {
    var navigator: Navigator? = null
}

/**
 * Provides Navigator to the composition tree
 */
@Composable
fun ProvideNavigator(
    navController: NavHostController,
    content: @Composable () -> Unit
) {
    val navigator = AppNavigator(navController)
    NavigatorHolder.navigator = navigator
    
    CompositionLocalProvider(
        LocalNavigator provides navigator
    ) {
        content()
    }
}
