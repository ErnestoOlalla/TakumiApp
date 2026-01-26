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

    val actions = EpisodeDetailsActions(
        start = ::start,
        onCharacterClick = ::onCharacterClick
    )

    private fun start(episodeId: String) {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            val result = getEpisodeDetailsUseCase(episodeId)
            if (result.isSuccess) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        episode = result.value()?.toUI()
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

    private fun onCharacterClick(characterId: String) {
        navigator.navigateTo(Destination.CharacterDetails(characterId))
    }
}

data class EpisodeDetailsUIState(
    val isLoading: Boolean = true,
    val episode: EpisodeDetailsUiModel? = null,
    val error: String? = null
)

data class EpisodeDetailsActions(
    val start: (String) -> Unit = {},
    val onCharacterClick: (String) -> Unit = {}
)
