package com.example.ualacitieschallenge.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ualacitieschallenge.data.model.City
import com.example.ualacitieschallenge.ui.theme.UalaCitiesChallengeTheme
import com.example.ualacitieschallenge.ui.viewmodel.CityViewModel

@Composable
fun MainScreen(
    viewModel: CityViewModel,
    navController: NavHostController = rememberNavController()
) {
    val selectedCity by viewModel.selectedCity.collectAsState()
    val configuration = LocalConfiguration.current

    if (configuration.screenWidthDp >= 600) {
        // Landscape or tablet layout
        Row(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.weight(1f)) {
                CityListScreen(
                    viewModel = viewModel,
                    onCitySelected = { viewModel.onCitySelected(it) }
                )
            }
            Box(modifier = Modifier.weight(1f)) {
                selectedCity?.let { city ->
                    CityDetailScreen(
                        city = city,
                        viewModel = viewModel,
                        onBackPressed = { viewModel.clearSelectedCity() }
                    )
                } ?: Text("Select a city to see details")
            }
        }
    } else {
        // Portrait layout
        if (selectedCity == null) {
            CityListScreen(
                viewModel = viewModel,
                onCitySelected = { viewModel.onCitySelected(it) }
            )
        } else {
            selectedCity?.let { city ->
                CityDetailScreen(
                    city = city,
                    viewModel = viewModel,
                    onBackPressed = { viewModel.clearSelectedCity() }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    UalaCitiesChallengeTheme {
        MainScreen(viewModel = CityViewModel())
    }
}