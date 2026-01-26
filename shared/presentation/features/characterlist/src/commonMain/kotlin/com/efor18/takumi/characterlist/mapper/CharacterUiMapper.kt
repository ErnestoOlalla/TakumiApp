package com.efor18.takumi.characterlist.mapper

import com.efor18.takumi.domain.entity.Character
import com.efor18.takumi.characterlist.model.CharacterUiModel

fun Character.toUI(): CharacterUiModel {
    return CharacterUiModel(
        id = id,
        name = name,
        imageUrl = imageUrl
    )
}
