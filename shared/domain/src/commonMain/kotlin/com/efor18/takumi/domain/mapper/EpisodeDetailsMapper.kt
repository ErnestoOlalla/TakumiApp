package com.efor18.takumi.domain.mapper

import com.efor18.takumi.common.KResult
import com.efor18.takumi.data.remote.rickandmorty.GetEpisodeQuery
import com.efor18.takumi.domain.entity.*

internal fun GetEpisodeQuery.Episode.toEntity(): EpisodeDetails {
    return EpisodeDetails(
        id = id.orEmpty(),
        name = name.orEmpty(),
        airDate = air_date,
        episode = episode.orEmpty(),
        characters = characters?.mapNotNull { it?.toEntity() } ?: emptyList(),
        created = created
    )
}

internal fun GetEpisodeQuery.Character.toEntity(): EpisodeCharacter {
    return EpisodeCharacter(
        id = id.orEmpty(),
        name = name.orEmpty(),
        imageUrl = image.orEmpty()
    )
}

internal fun KResult<GetEpisodeQuery.Episode>.toEntity(): KResult<EpisodeDetails> {
    return map { it.toEntity() }
}