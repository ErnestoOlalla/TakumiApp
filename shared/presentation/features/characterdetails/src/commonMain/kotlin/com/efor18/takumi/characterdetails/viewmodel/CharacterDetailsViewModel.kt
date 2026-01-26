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

    val actions = CharacterDetailsActions(
        start = ::start,
        onEpisodeClick = ::onEpisodeClick
    )

    private fun start(characterId: String) {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            val result = getCharacterDetailsUseCase(characterId)
            if (result.isSuccess) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        character = result.value()?.toUI()
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = result.error()?.message
                    )
                }
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

data class CharacterDetailsActions(
    val start: (String) -> Unit = {},
    val onEpisodeClick: (String) -> Unit = {}
)
