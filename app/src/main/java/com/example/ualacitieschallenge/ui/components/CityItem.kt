package com.example.ualacitieschallenge.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.ualacitieschallenge.R
import com.example.ualacitieschallenge.data.model.City

@Composable
fun CityItem(
    city: City,
    onCitySelected: (City) -> Unit,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onCitySelected(city) }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "${city.name}, ${city.country}",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = stringResource(R.string.coordinates_format, city.coord.lat, city.coord.lon),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            FavoriteButton(
                isFavorite = city.isFavorite,
                onToggleFavorite = onToggleFavorite
            )
        }
    }
}