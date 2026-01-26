package com.efor18.takumi.domain.mapper

import com.efor18.takumi.common.KResult
import com.efor18.takumi.data.remote.rickandmorty.AllCharactersQuery
import com.efor18.takumi.domain.entity.*

internal fun AllCharactersQuery.Result.toEntity(): Character {
    return Character(
        id = id.orEmpty(),
        name = name.orEmpty(),
        imageUrl = image.orEmpty()
    )
}

internal fun List<AllCharactersQuery.Result>.toEntity(): List<Character> {
    return map { it.toEntity() }
}

internal fun KResult<List<AllCharactersQuery.Result>>.toEntities(): KResult<List<Character>> {
    return map { it.toEntity() }
}
