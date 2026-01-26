package com.efor18.takumi.domain.usecase

import com.efor18.takumi.common.KResult
import com.efor18.takumi.data.repository.APIRepository
import com.efor18.takumi.domain.entity.CharacterDetails
import com.efor18.takumi.domain.mapper.toEntity

class GetCharacterDetailsUseCase(
    private val repository: APIRepository
) {
    suspend operator fun invoke(id: String): KResult<CharacterDetails> {
        return repository.getCharacterById(id).toEntity()
    }
}
