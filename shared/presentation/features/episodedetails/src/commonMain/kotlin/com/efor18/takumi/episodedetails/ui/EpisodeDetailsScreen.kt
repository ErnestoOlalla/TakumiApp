package com.efor18.takumi.episodedetails.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.efor18.takumi.episodedetails.model.EpisodeCharacterUiModel
import com.efor18.takumi.episodedetails.model.EpisodeDetailsUiModel
import com.efor18.takumi.episodedetails.viewmodel.EpisodeDetailsViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import com.efor18.takumi.common.formatToHumanReadableDate
import com.efor18.takumi.res.Res
import com.efor18.takumi.res.*
import org.jetbrains.compose.resources.stringResource

@Composable
@Preview
fun EpisodeDetailsScreen(
    episodeId: String,
    viewModel: EpisodeDetailsViewModel = koinViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    // Start data loading when the screen is first shown
    LaunchedEffect(Unit) {
        viewModel.actions.start(episodeId)
    }

    when {
        uiState.isLoading -> {
            LoadingEpisodeState()
        }

        uiState.error != null -> {
            ErrorEpisodeState(error = uiState.error)
        }

        uiState.episode != null -> {
            EpisodeDetails(
                episode = uiState.episode!!,
                onCharacterClick = viewModel.actions.onCharacterClick
            )
        }
    }
}

@Composable
private fun LoadingEpisodeState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorEpisodeState(error: String?) {
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
private fun EpisodeDetails(
    episode: EpisodeDetailsUiModel,
    onCharacterClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Episode Information
        item {
            EpisodeInfoCard(episode = episode)
        }

        // Characters
        if (episode.characters.isNotEmpty()) {
            item {
                Text(
                    text = stringResource(Res.string.characters_count, episode.characters.size),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            items(episode.characters) { character ->
                EpisodeCharacterCard(
                    character = character,
                    onCharacterClick = onCharacterClick
                )
            }
        }
    }
}

@Composable
private fun EpisodeInfoCard(episode: EpisodeDetailsUiModel) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = episode.name,
                style = MaterialTheme.typography.headlineMedium
            )
            
            InfoRow(label = stringResource(Res.string.episode_label), value = episode.episode)
            
            episode.airDate?.let { airDate ->
                InfoRow(label = stringResource(Res.string.air_date_label), value = formatToHumanReadableDate(airDate))
            }
            
            episode.created?.let { created ->
                InfoRow(label = stringResource(Res.string.created_label), value = formatToHumanReadableDate(created))
            }
        }
    }
}

@Composable
private fun EpisodeCharacterCard(
    character: EpisodeCharacterUiModel,
    onCharacterClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onCharacterClick(character.id)
            }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = character.imageUrl,
                contentDescription = stringResource(Res.string.character_image_desc, character.name),
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}