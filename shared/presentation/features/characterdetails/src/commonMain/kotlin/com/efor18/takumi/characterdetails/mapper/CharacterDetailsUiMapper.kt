package com.efor18.takumi.characterdetails.mapper

import com.efor18.takumi.domain.entity.CharacterDetails
import com.efor18.takumi.characterdetails.model.CharacterDetailsUiModel
import com.efor18.takumi.characterdetails.model.OriginUiModel
import com.efor18.takumi.characterdetails.model.LocationUiModel
import com.efor18.takumi.characterdetails.model.EpisodeUiModel

fun CharacterDetails.toUI(): CharacterDetailsUiModel {
    return CharacterDetailsUiModel(
        id = id,
        name = name,
        imageUrl = imageUrl,
        status = status,
        species = species,
        type = type,
        gender = gender,
        origin = origin?.let {
            OriginUiModel(
                name = it.name,
                dimension = it.dimension
            )
        },
        location = location?.let {
            LocationUiModel(name = it.name)
        },
        episodes = episodes.map {
            EpisodeUiModel(
                id = it.id,
                name = it.name,
                airDate = it.airDate
            )
        }
    )
}