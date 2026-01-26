package com.efor18.takumi.characterlist.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.efor18.takumi.characterlist.ui.CharacterListScreen
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Public destination - exposed to composeApp
@Serializable
@SerialName("characters")
data object Characters

// Internal destination - only used within this feature
@Serializable
@SerialName("characters-list")
internal data object CharactersList

/**
 * Defines the navigation graph for the characters feature.
 */
fun NavGraphBuilder.charactersGraph(
    navController: NavHostController
) {
    navigation<Characters>(
        startDestination = CharactersList
    ) {
        composable<CharactersList> {
            CharacterListScreen()
        }
    }
}
