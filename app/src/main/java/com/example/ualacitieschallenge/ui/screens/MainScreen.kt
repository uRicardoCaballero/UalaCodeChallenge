package com.example.ualacitieschallenge.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ualacitieschallenge.data.model.City
import com.example.ualacitieschallenge.ui.components.CityItem
import com.example.ualacitieschallenge.ui.components.SearchBar
import com.example.ualacitieschallenge.ui.viewmodel.CityViewModel


@Composable
fun MainScreen(
    viewModel: CityViewModel,
    onCitySelected: (City) -> Unit // Explicitly define the type of city
) {
    val cities by viewModel.cities.collectAsState(initial = emptyList())
    val isLoading by viewModel.isLoading.collectAsState()
    val showOnlyFavorites by viewModel.showOnlyFavorites.collectAsState()
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Handle errors (if any)
    LaunchedEffect(Unit) {
        viewModel.errorMessage.collect { message ->
            errorMessage = message
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            onQueryChanged = viewModel::onSearchQueryChanged,
            showOnlyFavorites = showOnlyFavorites,
            onShowOnlyFavoritesChanged = viewModel::onShowOnlyFavoritesChanged
        )

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.fillMaxSize().wrapContentSize())
        } else if (errorMessage != null) {
            // Display error message
            ErrorMessage(errorMessage!!)
        } else {
            if (cities.isEmpty()) {
                // Display a message when no cities are found
                NoCitiesFoundMessage()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(cities) { city ->
                        CityItem(
                            city = city,
                            onCitySelected = onCitySelected, // Pass the onCitySelected lambda
                            onToggleFavorite = { viewModel.onToggleFavorite(city) }
                        )
                    }
                }
            }
        }
    }
}
