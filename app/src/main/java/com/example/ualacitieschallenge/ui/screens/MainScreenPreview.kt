package com.example.ualacitieschallenge.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.ualacitieschallenge.data.local.CityDao
import com.example.ualacitieschallenge.data.model.City
import com.example.ualacitieschallenge.data.model.CityEntity
import com.example.ualacitieschallenge.data.model.Coordinates
import com.example.ualacitieschallenge.data.remote.CityApi
import com.example.ualacitieschallenge.data.repository.FakeCityRepository
import com.example.ualacitieschallenge.ui.theme.UalaCitiesChallengeTheme
import com.example.ualacitieschallenge.ui.viewmodel.CityViewModel
import retrofit2.Response


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    // Create a fake repository
    val fakeRepository = FakeCityRepository()

    // Create mock dependencies for cityApi and cityDao
    val mockCityApi = object : CityApi {
        override suspend fun getCities(url: String): Response<List<City>> {
            // Return a mock response
            return Response.success(
                listOf(
                    City(id = 1, name = "New York", country = "US", coordinates = Coordinates(40.7128, -74.0060), isFavorite = false),
                    City(id = 2, name = "Los Angeles", country = "US", coordinates = Coordinates(34.0522, -118.2437), isFavorite = true)
                )
            )
        }
    }

    val mockCityDao = object : CityDao {
        override fun searchCities(prefix: String): List<CityEntity> {
            return emptyList()
        }

        override fun searchFavoriteCities(prefix: String): List<CityEntity> {
            return emptyList()
        }

        override fun insertAll(cities: List<CityEntity>) {
            // No-op for mock
        }

        override fun updateFavoriteStatus(cityId: String, isFavorite: Boolean) {
            // No-op for mock
        }

        override fun getFavoriteCities(): List<CityEntity> {
            return emptyList()
        }
    }

    // Create the ViewModel with the fake repository and mock dependencies
    val viewModel = CityViewModel(fakeRepository, mockCityApi, mockCityDao)

    // Preview the MainScreen
    UalaCitiesChallengeTheme {
        MainScreen(viewModel = viewModel, onCitySelected = {}) // Remove navigation logic
    }
}