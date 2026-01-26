package com.efor18.takumi.data.repository

import com.efor18.takumi.common.KResult
import com.efor18.takumi.data.remote.RickAndMortyDataSource
import com.efor18.takumi.data.remote.rickandmorty.AllCharactersQuery
import com.efor18.takumi.data.remote.rickandmorty.GetCharacterQuery
import com.efor18.takumi.data.remote.rickandmorty.GetEpisodeQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

internal class APIRepositoryImpl : APIRepository {

    private val dataSource = RickAndMortyDataSource()

    override suspend fun getAllCharacters(): KResult<List<AllCharactersQuery.Result>> {
        return withContext(Dispatchers.IO) { dataSource.getAllCharacters() }
    }

    override suspend fun getCharacterById(id: String): KResult<GetCharacterQuery.Character> {
        return withContext(Dispatchers.IO) { dataSource.getCharacterById(id) }
    }

    override suspend fun getEpisodeById(id: String): KResult<GetEpisodeQuery.Episode> {
        return withContext(Dispatchers.IO) { dataSource.getEpisodeById(id) }
    }
}