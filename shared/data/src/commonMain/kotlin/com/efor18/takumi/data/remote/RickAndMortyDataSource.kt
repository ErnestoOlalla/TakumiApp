package com.efor18.takumi.data.remote

import co.touchlab.kermit.Logger
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloResponse
import com.apollographql.apollo.api.Operation
import com.efor18.takumi.common.ERROR_UNKNOWN
import com.efor18.takumi.common.KError
import com.efor18.takumi.common.KErrorType
import com.efor18.takumi.common.KResult
import com.efor18.takumi.common.runKatching
import com.efor18.takumi.data.remote.rickandmorty.AllCharactersQuery
import com.efor18.takumi.data.remote.rickandmorty.GetCharacterQuery
import com.efor18.takumi.data.remote.rickandmorty.GetEpisodeQuery
import kotlinx.coroutines.delay

internal class RickAndMortyDataSource {

    private val apolloClient = ApolloClient.Builder()
        .serverUrl("https://rickandmortyapi.com/graphql")
        .webSocketReopenWhen { throwable, attempt ->
            Logger.d("Apollo - WebSocket got disconnected, reopening after a delay", throwable)
            delay(attempt * 1000)
            true
        }
        .build()

    suspend fun getAllCharacters(): KResult<List<AllCharactersQuery.Result>> {
        return runKatching {
            val response: ApolloResponse<AllCharactersQuery.Data> =
                apolloClient.query(AllCharactersQuery()).execute()
            val listResult = response.data?.characters?.results
            return if (response.exception == null && response.errors == null && listResult != null) {
                KResult.success(listResult.filterNotNull())
            } else {
                manageApolloError(response)
            }
        }
    }

    suspend fun getCharacterById(id: String): KResult<GetCharacterQuery.Character> {
        return runKatching {
            val response: ApolloResponse<GetCharacterQuery.Data> =
                apolloClient.query(GetCharacterQuery(id = id)).execute()
            val character = response.data?.character
            return if (response.exception == null && response.errors == null && character != null) {
                KResult.success(character)
            } else {
                manageApolloError(response)
            }
        }
    }

    suspend fun getEpisodeById(id: String): KResult<GetEpisodeQuery.Episode> {
        return runKatching {
            val response: ApolloResponse<GetEpisodeQuery.Data> =
                apolloClient.query(GetEpisodeQuery(id = id)).execute()
            val episode = response.data?.episode
            return if (response.exception == null && response.errors == null && episode != null) {
                KResult.success(episode)
            } else {
                manageApolloError(response)
            }
        }
    }

    private fun <K : Operation.Data, T> manageApolloError(response: ApolloResponse<K>): KResult<T> {
        val exception = response.exception
        val errors = response.errors
        val kError = if (exception != null) {
            KError(
                type = KErrorType.UNKNOWN,
                message = exception.message ?: "",
                throwable = exception
            )
        } else if (errors != null) {
            KError(
                type = KErrorType.UNKNOWN,
                message = errors.toString(),
                throwable = exception
            )
        } else {
            ERROR_UNKNOWN
        }
        return KResult.error(kError)
    }
}