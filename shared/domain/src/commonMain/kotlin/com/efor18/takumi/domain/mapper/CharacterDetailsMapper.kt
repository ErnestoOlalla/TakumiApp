package com.efor18.takumi.domain.mapper

import com.efor18.takumi.common.KResult
import com.efor18.takumi.data.remote.rickandmorty.GetCharacterQuery
import com.efor18.takumi.domain.entity.*

internal fun GetCharacterQuery.Character.toEntity(): CharacterDetails {
    return CharacterDetails(
        id = id.orEmpty(),
        name = name.orEmpty(),
        imageUrl = image.orEmpty(),
        status = status,
        species = species,
        type = type,
        gender = gender,
        origin = origin?.let {
            Origin(
                name = it.name.orEmpty(),
                dimension = it.dimension
            )
        },
        location = location?.let {
            Location(name = it.name.orEmpty())
        },
        episodes = episode?.mapNotNull { it?.toEntity() } ?: emptyList()
    )
}

internal fun GetCharacterQuery.Episode.toEntity(): Episode {
    return Episode(
        id = id.orEmpty(),
        name = name.orEmpty(),
        airDate = air_date
    )
}

internal fun KResult<GetCharacterQuery.Character>.toEntity(): KResult<CharacterDetails> {
    return map { it.toEntity() }
}
