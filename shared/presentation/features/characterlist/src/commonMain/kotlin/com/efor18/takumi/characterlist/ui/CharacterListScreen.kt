package com.efor18.takumi.characterlist.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import coil3.compose.AsyncImage
import com.efor18.takumi.characterlist.model.CharacterUiModel
import com.efor18.takumi.characterlist.viewmodel.CharacterListIntent
import com.efor18.takumi.characterlist.viewmodel.CharacterListViewModel
import com.efor18.takumi.res.Res
import com.efor18.takumi.res.characters_title
import com.efor18.takumi.res.error_prefix
import com.efor18.takumi.res.error_unknown
import com.efor18.takumi.res.character_avatar_desc
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun CharacterListScreen(
    viewModel: CharacterListViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sendIntent(CharacterListIntent.Start)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TitleBar(title = stringResource(Res.string.characters_title))

        when {
            uiState.isLoading -> {
                LoadingState()
            }

            uiState.error != null -> {
                ErrorState(error = uiState.error)
            }

            else -> {
                CharacterList(
                    characters = uiState.characters,
                    onCharacterClick = { character ->
                        viewModel.sendIntent(CharacterListIntent.CharacterClicked(character.id))
                    }
                )
            }
        }
    }
}

@Composable
private fun TitleBar(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
private fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorState(error: String?) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(Res.string.error_prefix) + (error ?: stringResource(Res.string.error_unknown)),
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
private fun CharacterList(
    characters: List<CharacterUiModel>,
    onCharacterClick: (CharacterUiModel) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(characters) { character ->
            CharacterCard(
                character = character,
                onClick = { onCharacterClick(character) }
            )
        }
    }
}

@Composable
private fun CharacterCard(
    character: CharacterUiModel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Logger.d { "Loading image for ${character.name}: ${character.imageUrl}" }

            if (character.imageUrl.isNotEmpty()) {
                AsyncImage(
                    model = character.imageUrl,
                    contentDescription = stringResource(Res.string.character_avatar_desc, character.name),
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape),
                    onSuccess = { Logger.d { "Image loaded successfully for ${character.name}" } },
                    onError = {
                        Logger.e(
                            "Failed to load image for ${character.name}",
                            it.result.throwable
                        )
                    }
                )
            } else {
                // Fallback placeholder when no image URL is available
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = character.name.firstOrNull()?.toString() ?: "?",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Text(
                text = character.name,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
