package com.efor18.takumi.domain.usecase

import com.efor18.takumi.common.KResult
import com.efor18.takumi.data.repository.APIRepository
import com.efor18.takumi.domain.entity.Character
import com.efor18.takumi.domain.mapper.toEntities

class GetCharactersUseCase(
    private val repository: APIRepository
) {
    suspend operator fun invoke(): KResult<List<Character>> {
        return repository.getAllCharacters().toEntities()
    }
}