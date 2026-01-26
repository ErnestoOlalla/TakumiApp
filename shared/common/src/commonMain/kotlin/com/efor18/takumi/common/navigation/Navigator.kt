package com.efor18.takumi.common.navigation

/**
 * Navigation abstraction that allows features to navigate without depending on specific navigation implementations.
 * This provides a clean boundary between feature modules and navigation logic.
 *
 * Features can use this interface to navigate to other features without knowing their specific destination types.
 */
interface Navigator {
    /**
     * Navigate to any destination that can be serialized
     */
    fun navigateTo(destination: Destination)

//    /**
//     * Navigate to character details screen
//     */
//    fun navigateToCharacterDetails(characterId: String)
//
//    /**
//     * Navigate to episode details screen
//     */
//    fun navigateToEpisodeDetails(episodeId: String)
//
    /**
     * Navigate back to previous screen
     */
    fun navigateBack()

//    /**
//     * Navigate to characters list screen
//     */
//    fun navigateToCharactersList()
}
