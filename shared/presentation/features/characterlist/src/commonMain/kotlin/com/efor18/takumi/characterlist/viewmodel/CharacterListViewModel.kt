package com.efor18.takumi.characterlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.efor18.takumi.characterlist.mapper.toUI
import com.efor18.takumi.characterlist.model.CharacterUiModel
import com.efor18.takumi.common.navigation.Destination
import com.efor18.takumi.common.navigation.Navigator
import com.efor18.takumi.domain.usecase.GetCharactersUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterListViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val navigator: Navigator,
) : ViewModel() {

    // ----------------------------------------
    // STATE
    // ----------------------------------------
    private val _uiState = MutableStateFlow(CharacterListUIState())
    val uiState = _uiState.asStateFlow()

    // ----------------------------------------
    // EFFECTS (one-shot events)
    // ----------------------------------------
    private val _effects = MutableSharedFlow<CharacterListEffect>()
    val effects: SharedFlow<CharacterListEffect> = _effects.asSharedFlow()


    // ----------------------------------------
    // PUBLIC API (UI → ViewModel)
    // ----------------------------------------
    fun sendIntent(intent: CharacterListIntent) {
        when (intent) {
            CharacterListIntent.Start -> loadCharacters()
            is CharacterListIntent.CharacterClicked -> {
                emitEffect(
                    CharacterListEffect.NavigateToDetail(intent.id)
                )
            }
        }
    }

    // ------------------------
    // PRIVATE
    // ------------------------

    private fun dispatch(result: CharacterListResult) {
        _uiState.update { current ->
            characterListReducer(current, result)
        }
    }

    private fun loadCharacters() {
        dispatch(CharacterListResult.Loading)
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            val characters = getCharactersUseCase()
            if (characters.isSuccess) {
                dispatch(
                    CharacterListResult.Success(
                        characters.value()?.map { character -> character.toUI() } ?: emptyList()
                    )
                )
            } else {
                dispatch(
                    CharacterListResult.Error(
                        characters.error()?.message ?: "Something went wrong"
                    )
                )
            }
        }
    }

    private fun emitEffect(effect: CharacterListEffect) {
        viewModelScope.launch {
            _effects.emit(effect)

            // Navegación real (si decides manejarla aquí)
            if (effect is CharacterListEffect.NavigateToDetail) {
                navigator.navigateTo(
                    Destination.CharacterDetails(effect.id)
                )
            }
        }
    }
}

// ----------------------------------------
// STATE
// ----------------------------------------

data class CharacterListUIState(
    val isLoading: Boolean = false,
    val characters: List<CharacterUiModel> = emptyList(),
    val error: String? = null
)

// ----------------------------------------
// INTENTS (UI → VM)
// ----------------------------------------

sealed interface CharacterListIntent {
    data object Start : CharacterListIntent
    data class CharacterClicked(val id: String) : CharacterListIntent
}

// ----------------------------------------
// RESULTS (Internal state transitions)
// ----------------------------------------

sealed interface CharacterListResult {
    data object Loading : CharacterListResult
    data class Success(val characters: List<CharacterUiModel>) : CharacterListResult
    data class Error(val message: String?) : CharacterListResult
}

// ----------------------------------------
// EFFECTS (One-shot events)
// ----------------------------------------

sealed interface CharacterListEffect {
    data class NavigateToDetail(val id: String) : CharacterListEffect
}

// ----------------------------------------
// REDUCER (PURE FUNCTION)
// ----------------------------------------
fun characterListReducer(
    state: CharacterListUIState,
    result: CharacterListResult
): CharacterListUIState {
    return when (result) {
        CharacterListResult.Loading -> state.copy(isLoading = true, error = null)
        is CharacterListResult.Error -> state.copy(isLoading = false, error = result.message)
        is CharacterListResult.Success -> state.copy(
            isLoading = false,
            characters = result.characters
        )
    }
}