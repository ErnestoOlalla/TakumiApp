package com.efor18.takumi.characterdetails.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.efor18.takumi.characterdetails.ui.CharacterDetailsScreen
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Public destination - exposed to composeApp
@Serializable
@SerialName("character-details")
data class CharacterDetails(val characterId: String = "")

// Navigation extension function
fun NavController.navigateToCharacterDetails(
    characterId: String,
    navOptions: NavOptions? = null
) = navigate(route = CharacterDetails(characterId), navOptions)

/**
 * Defines the navigation graph for the character details feature.
 */
fun NavGraphBuilder.characterDetailsGraph() {
    composable<CharacterDetails> { backStackEntry ->
        val args = backStackEntry.toRoute<CharacterDetails>()
        CharacterDetailsScreen(
            characterId = args.characterId
        )
    }
}
