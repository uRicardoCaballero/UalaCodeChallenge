package com.example.ualacitieschallenge.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.ualacitieschallenge.R
import com.example.ualacitieschallenge.data.model.City
import com.example.ualacitieschallenge.ui.components.FavoriteButton
import com.example.ualacitieschallenge.ui.viewmodel.CityViewModel

@Composable
fun CityDetailScreen(
    city: City,
    viewModel: CityViewModel,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "${city.name}, ${city.country}",
                style = MaterialTheme.typography.headlineMedium
            )
            FavoriteButton(
                isFavorite = city.isFavorite,
                onToggleFavorite = { viewModel.onToggleFavorite(city) }
            )
        }

        Text(
            text = stringResource(R.string.coordinates_format, city.coord.lat, city.coord.lon),
            style = MaterialTheme.typography.bodyLarge
        )

        // Map would be implemented here in a real app
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Text(
                text = "Map would be displayed here",
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Button(
            onClick = onBackPressed,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = stringResource(R.string.back))
        }
    }
}