package com.efor18.takumi.characterdetails.model

data class CharacterDetailsUiModel(
    val id: String,
    val name: String,
    val imageUrl: String,
    val status: String? = null,
    val species: String? = null,
    val type: String? = null,
    val gender: String? = null,
    val origin: OriginUiModel? = null,
    val location: LocationUiModel? = null,
    val episodes: List<EpisodeUiModel> = emptyList()
)

data class OriginUiModel(
    val name: String,
    val dimension: String? = null
)

data class LocationUiModel(
    val name: String
)

data class EpisodeUiModel(
    val id: String,
    val name: String,
    val airDate: String? = null
)