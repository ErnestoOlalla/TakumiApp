package com.efor18.takumi.domain.entity

data class CharacterDetails(
    val id: String,
    val name: String,
    val imageUrl: String,
    val status: String? = null,
    val species: String? = null,
    val type: String? = null,
    val gender: String? = null,
    val origin: Origin? = null,
    val location: Location? = null,
    val episodes: List<Episode> = emptyList()
)

data class Origin(
    val name: String,
    val dimension: String? = null
)

data class Location(
    val name: String
)

data class Episode(
    val id: String,
    val name: String,
    val airDate: String? = null
)

