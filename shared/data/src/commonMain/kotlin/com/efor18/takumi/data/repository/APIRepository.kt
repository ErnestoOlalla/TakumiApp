package com.efor18.takumi.data.repository

import com.efor18.takumi.common.KResult
import com.efor18.takumi.data.remote.rickandmorty.AllCharactersQuery
import com.efor18.takumi.data.remote.rickandmorty.GetCharacterQuery
import com.efor18.takumi.data.remote.rickandmorty.GetEpisodeQuery

interface APIRepository {

    suspend fun getAllCharacters(): KResult<List<AllCharactersQuery.Result>>
    suspend fun getCharacterById(id: String): KResult<GetCharacterQuery.Character>
    suspend fun getEpisodeById(id: String): KResult<GetEpisodeQuery.Episode>
}
