package com.efor18.takumi.common.navigation

import kotlinx.serialization.Serializable

/**
 * Common navigation destinations that can be used across all feature modules.
 * This provides type-safe navigation without coupling features to specific navigation implementations.
 */
@Serializable
sealed class Destination {
    @Serializable
    data object Characters : Destination()

    @Serializable
    data class CharacterDetails(val characterId: String = "") : Destination()

    @Serializable
    data class EpisodeDetails(val episodeId: String = "") : Destination()
}