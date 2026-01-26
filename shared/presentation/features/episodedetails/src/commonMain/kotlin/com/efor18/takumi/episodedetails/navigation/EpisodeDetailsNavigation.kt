package com.efor18.takumi.episodedetails.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.efor18.takumi.episodedetails.ui.EpisodeDetailsScreen
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Public destination - exposed to composeApp
@Serializable
@SerialName("episode-details")
data class EpisodeDetails(val episodeId: String = "")

// Navigation extension function
fun NavController.navigateToEpisodeDetails(
    episodeId: String,
    navOptions: NavOptions? = null
) = navigate(route = EpisodeDetails(episodeId), navOptions)

/**
 * Defines the navigation graph for the episode details feature.
 */
fun NavGraphBuilder.episodeDetailsGraph() {
    composable<EpisodeDetails> { backStackEntry ->
        val args = backStackEntry.toRoute<EpisodeDetails>()
        EpisodeDetailsScreen(episodeId = args.episodeId)
    }
}
