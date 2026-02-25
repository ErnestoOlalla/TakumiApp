package com.efor18.takumi.episodedetails.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.efor18.takumi.common.navigation.Destination
import com.efor18.takumi.common.navigation.Navigator
import com.efor18.takumi.domain.usecase.GetEpisodeDetailsUseCase
import com.efor18.takumi.episodedetails.mapper.toUI
import com.efor18.takumi.episodedetails.model.EpisodeDetailsUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EpisodeDetailsViewModel(
    private val getEpisodeDetailsUseCase: GetEpisodeDetailsUseCase,
    private val navigator: Navigator
) : ViewModel() {

    private val _uiState = MutableStateFlow(EpisodeDetailsUIState())
    val uiState = _uiState.asStateFlow()

    fun sendIntent(intent: EpisodeDetailsIntents) {
        when (intent) {
            is EpisodeDetailsIntents.Start -> start(intent.id)
            is EpisodeDetailsIntents.CharacterClick -> onCharacterClick(intent.id)
        }
    }

    private fun dispatch(result: EpisodeDetailsResult) {
        _uiState.update {
            episodeDetailsReducer(_uiState.value, result)
        }
    }

    private fun start(episodeId: String) {
        dispatch(EpisodeDetailsResult.Loading)
        viewModelScope.launch {
            val result = getEpisodeDetailsUseCase(episodeId)
            if (result.isSuccess) {
                dispatch(EpisodeDetailsResult.Success(result.value()?.toUI()))
            } else {
                dispatch(EpisodeDetailsResult.Error(result.error()?.message))
            }
        }
    }

    private fun onCharacterClick(characterId: String) {
        navigator.navigateTo(Destination.CharacterDetails(characterId))
    }
}

data class EpisodeDetailsUIState(
    val isLoading: Boolean = true,
    val episode: EpisodeDetailsUiModel? = null,
    val error: String? = null
)

sealed interface EpisodeDetailsIntents {
    data class Start(val id: String) : EpisodeDetailsIntents
    data class CharacterClick(val id: String) : EpisodeDetailsIntents
}

sealed interface EpisodeDetailsResult {
    data object Loading : EpisodeDetailsResult
    data class Success(val episode: EpisodeDetailsUiModel?) : EpisodeDetailsResult
    data class Error(val message: String?) : EpisodeDetailsResult

}

fun episodeDetailsReducer(
    state: EpisodeDetailsUIState,
    result: EpisodeDetailsResult
): EpisodeDetailsUIState {
    return when (result) {
        EpisodeDetailsResult.Loading -> state.copy(isLoading = true, error = null)
        is EpisodeDetailsResult.Error -> state.copy(isLoading = false, error = result.message)
        is EpisodeDetailsResult.Success -> state.copy(
            isLoading = false,
            episode = result.episode
        )
    }
}