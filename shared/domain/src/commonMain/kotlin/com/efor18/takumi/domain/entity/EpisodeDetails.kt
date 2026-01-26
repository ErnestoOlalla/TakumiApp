package com.efor18.takumi.domain.entity

data class EpisodeDetails(
    val id: String,
    val name: String,
    val airDate: String? = null,
    val episode: String,
    val characters: List<EpisodeCharacter> = emptyList(),
    val created: String? = null
)

data class EpisodeCharacter(
    val id: String,
    val name: String,
    val imageUrl: String
)