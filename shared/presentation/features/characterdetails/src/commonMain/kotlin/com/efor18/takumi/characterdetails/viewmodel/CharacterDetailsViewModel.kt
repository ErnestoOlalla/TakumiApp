package com.efor18.takumi.characterdetails.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.efor18.takumi.characterdetails.mapper.toUI
import com.efor18.takumi.characterdetails.model.CharacterDetailsUiModel
import com.efor18.takumi.common.navigation.Destination
import com.efor18.takumi.common.navigation.Navigator
import com.efor18.takumi.domain.usecase.GetCharacterDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterDetailsViewModel(
    private val getCharacterDetailsUseCase: GetCharacterDetailsUseCase,
    private val navigator: Navigator
) : ViewModel() {

    private val _uiState = MutableStateFlow(CharacterDetailsUIState())
    val uiState = _uiState.asStateFlow()

    fun sendIntent(intent: CharacterDetailsIntents) {
        when (intent) {
            is CharacterDetailsIntents.Start -> loadCharacter(intent.id)
            is CharacterDetailsIntents.EpisodeClick -> onEpisodeClick(intent.id)
        }
    }

    private fun dispatch(result: CharacterDetailsResults) {
        _uiState.update {
            characterDetailsReducer(_uiState.value, result)
        }
    }


    private fun loadCharacter(characterId: String) {
        dispatch(CharacterDetailsResults.Loading)
        viewModelScope.launch {
            val result = getCharacterDetailsUseCase(characterId)
            if (result.isSuccess) {
                dispatch(CharacterDetailsResults.Success(result.value()?.toUI()))
            } else {
                dispatch(CharacterDetailsResults.Error(result.error()?.message))
            }
        }
    }

    private fun onEpisodeClick(episodeId: String) {
        navigator.navigateTo(Destination.EpisodeDetails(episodeId))
    }
}

data class CharacterDetailsUIState(
    val isLoading: Boolean = true,
    val character: CharacterDetailsUiModel? = null,
    val error: String? = null
)

sealed interface CharacterDetailsIntents {
    data class Start(val id: String) : CharacterDetailsIntents
    data class EpisodeClick(val id: String) : CharacterDetailsIntents
}

sealed interface CharacterDetailsResults {
    data object Loading : CharacterDetailsResults
    data class Success(val character: CharacterDetailsUiModel?) : CharacterDetailsResults
    data class Error(val message: String?) : CharacterDetailsResults
}

fun characterDetailsReducer(
    state: CharacterDetailsUIState,
    result: CharacterDetailsResults
): CharacterDetailsUIState {
    return when (result) {
        CharacterDetailsResults.Loading -> state.copy(isLoading = true, error = null)
        is CharacterDetailsResults.Error -> state.copy(isLoading = false, error = result.message)
        is CharacterDetailsResults.Success -> state.copy(
            isLoading = false,
            character = result.character
        )
    }
}