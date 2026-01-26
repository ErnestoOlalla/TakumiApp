package com.efor18.takumi.navigation

import androidx.navigation.NavHostController
import com.efor18.takumi.characterdetails.navigation.navigateToCharacterDetails
import com.efor18.takumi.characterlist.navigation.Characters
import com.efor18.takumi.common.navigation.Destination
import com.efor18.takumi.common.navigation.Navigator
import com.efor18.takumi.episodedetails.navigation.navigateToEpisodeDetails

/**
 * Concrete implementation of Navigator that uses NavHostController to perform navigation.
 * This implementation lives in the app module and bridges the gap between feature modules
 * and the actual navigation framework.
 *
 * Supports both global destinations and feature-specific destinations.
 */
class AppNavigator(
    private val navController: NavHostController
) : Navigator {

    override fun navigateTo(destination: Destination) {
        when (destination) {
            is Destination.CharacterDetails -> {
                navController.navigateToCharacterDetails(destination.characterId)
            }

            is Destination.EpisodeDetails -> {
                navController.navigateToEpisodeDetails(destination.episodeId)
            }

            is Destination.Characters -> {
                navController.navigate(Characters)
            }
        }
    }

    override fun navigateBack() {
        navController.popBackStack()
    }
}
