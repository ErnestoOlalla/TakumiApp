package com.efor18.takumi.episodedetails.mapper

import com.efor18.takumi.domain.entity.EpisodeDetails
import com.efor18.takumi.episodedetails.model.EpisodeDetailsUiModel
import com.efor18.takumi.episodedetails.model.EpisodeCharacterUiModel

fun EpisodeDetails.toUI(): EpisodeDetailsUiModel {
    return EpisodeDetailsUiModel(
        id = id,
        name = name,
        airDate = airDate,
        episode = episode,
        characters = characters.map {
            EpisodeCharacterUiModel(
                id = it.id,
                name = it.name,
                imageUrl = it.imageUrl
            )
        },
        created = created
    )
}