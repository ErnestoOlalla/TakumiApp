package com.efor18.takumi.characterlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.efor18.takumi.characterlist.mapper.toUI
import com.efor18.takumi.characterlist.model.CharacterUiModel
import com.efor18.takumi.common.navigation.Destination
import com.efor18.takumi.common.navigation.Navigator
import com.efor18.takumi.domain.usecase.GetCharactersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterListViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val navigator: Navigator,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CharacterListUIState())
    val uiState = _uiState.asStateFlow()

    val actions = CharacterListActions(
        start = ::start,
        onCharacterClick = ::onCharacterClick
    )

    private fun start() {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            val characters = getCharactersUseCase()
            if (characters.isSuccess) {
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        characters = characters.value()?.map { character -> character.toUI() }
                            ?: emptyList()
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = characters.error()?.message
                    )
                }
            }
        }
    }

    private fun onCharacterClick(characterId: String) {
        navigator.navigateTo(Destination.CharacterDetails(characterId))
    }
}

data class CharacterListUIState(
    val isLoading: Boolean = true,
    val characters: List<CharacterUiModel> = emptyList(),
    val error: String? = null
)

data class CharacterListActions(
    val start: () -> Unit = {},
    val onCharacterClick: (String) -> Unit = {},
)
