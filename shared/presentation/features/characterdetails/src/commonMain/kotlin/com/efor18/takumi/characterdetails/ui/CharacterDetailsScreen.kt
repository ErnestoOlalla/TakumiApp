package com.efor18.takumi.characterdetails.ui

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
import com.efor18.takumi.characterdetails.model.CharacterDetailsUiModel
import com.efor18.takumi.characterdetails.model.EpisodeUiModel
import com.efor18.takumi.characterdetails.viewmodel.CharacterDetailsIntents
import com.efor18.takumi.characterdetails.viewmodel.CharacterDetailsViewModel
import com.efor18.takumi.common.formatToHumanReadableDate
import com.efor18.takumi.res.Res
import com.efor18.takumi.res.aired_prefix
import com.efor18.takumi.res.character_image_desc
import com.efor18.takumi.res.character_info
import com.efor18.takumi.res.episodes_title
import com.efor18.takumi.res.error_prefix
import com.efor18.takumi.res.error_unknown
import com.efor18.takumi.res.gender_label
import com.efor18.takumi.res.last_location_label
import com.efor18.takumi.res.origin_dimension_label
import com.efor18.takumi.res.origin_label
import com.efor18.takumi.res.origin_location_title
import com.efor18.takumi.res.species_label
import com.efor18.takumi.res.status_label
import com.efor18.takumi.res.type_label
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun CharacterDetailsScreen(
    characterId: String,
    viewModel: CharacterDetailsViewModel = koinViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    // Start data loading when the screen is first shown
    LaunchedEffect(Unit) {
        viewModel.sendIntent(CharacterDetailsIntents.Start(characterId))
    }

    when {
        uiState.isLoading -> {
            LoadingDetailsState()
        }

        uiState.error != null -> {
            ErrorDetailsState(error = uiState.error)
        }

        uiState.character != null -> {
            CharacterDetails(
                character = uiState.character!!,
                onEpisodeClick = { episodeId ->
                    viewModel.sendIntent(CharacterDetailsIntents.EpisodeClick(episodeId))
                }
            )
        }
    }
}

@Composable
private fun LoadingDetailsState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorDetailsState(error: String?) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(Res.string.error_prefix) + (error
                ?: stringResource(Res.string.error_unknown)),
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
private fun CharacterDetails(
    character: CharacterDetailsUiModel,
    onEpisodeClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Character Image and Name
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = character.imageUrl,
                    contentDescription = stringResource(
                        Res.string.character_image_desc,
                        character.name
                    ),
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }

        // Basic Info
        item {
            CharacterInfoCard(character = character)
        }

        // Origin and Location
        item {
            OriginLocationCard(character = character)
        }

        // Episodes
        if (character.episodes.isNotEmpty()) {
            item {
                Text(
                    text = stringResource(Res.string.episodes_title),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            items(character.episodes) { episode ->
                EpisodeCard(
                    episode = episode,
                    onEpisodeClick = onEpisodeClick
                )
            }
        }
    }
}

@Composable
private fun CharacterInfoCard(character: CharacterDetailsUiModel) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(Res.string.character_info),
                style = MaterialTheme.typography.titleMedium
            )

            character.status?.let { status ->
                InfoRow(label = stringResource(Res.string.status_label), value = status)
            }

            character.species?.let { species ->
                InfoRow(label = stringResource(Res.string.species_label), value = species)
            }

            character.type?.takeIf { it.isNotBlank() }?.let { type ->
                InfoRow(label = stringResource(Res.string.type_label), value = type)
            }

            character.gender?.let { gender ->
                InfoRow(label = stringResource(Res.string.gender_label), value = gender)
            }
        }
    }
}

@Composable
private fun OriginLocationCard(character: CharacterDetailsUiModel) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(Res.string.origin_location_title),
                style = MaterialTheme.typography.titleMedium
            )

            character.origin?.let { origin ->
                InfoRow(label = stringResource(Res.string.origin_label), value = origin.name)
                origin.dimension?.let { dimension ->
                    InfoRow(
                        label = stringResource(Res.string.origin_dimension_label),
                        value = dimension
                    )
                }
            }

            character.location?.let { location ->
                InfoRow(
                    label = stringResource(Res.string.last_location_label),
                    value = location.name
                )
            }
        }
    }
}

@Composable
private fun EpisodeCard(
    episode: EpisodeUiModel,
    onEpisodeClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onEpisodeClick(episode.id)
            }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = episode.name,
                    style = MaterialTheme.typography.bodyLarge
                )
                episode.airDate?.let { airDate ->
                    Text(
                        text = stringResource(Res.string.aired_prefix) + formatToHumanReadableDate(
                            airDate
                        ),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
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