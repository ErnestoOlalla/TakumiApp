package com.efor18.takumi.episodedetails.model

data class EpisodeDetailsUiModel(
    val id: String,
    val name: String,
    val airDate: String? = null,
    val episode: String,
    val characters: List<EpisodeCharacterUiModel> = emptyList(),
    val created: String? = null
)

data class EpisodeCharacterUiModel(
    val id: String,
    val name: String,
    val imageUrl: String
)