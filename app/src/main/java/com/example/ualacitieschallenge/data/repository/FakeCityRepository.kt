package com.example.ualacitieschallenge.data.repository

import com.example.ualacitieschallenge.data.model.City
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCityRepository() : CityRepository(null, null) {

    // In-memory list to simulate the database
    private val fakeCities = mutableListOf<City>()

    init {
        // Initialize with some fake data
        fakeCities.addAll(
            listOf(
                City(
                    id = 1, name = "New York", isFavorite = false,
                    country = TODO(),
                    coordinates = TODO()
                ),
                City(
                    id = 2, name = "Los Angeles", isFavorite = true,
                    country = TODO(),
                    coordinates = TODO()
                ),
                City(
                    id = 3, name = "Chicago", isFavorite = false,
                    country = TODO(),
                    coordinates = TODO()
                ),
                City(
                    id = 4, name = "Houston", isFavorite = true,
                    country = TODO(),
                    coordinates = TODO()
                ),
                City(
                    id = 5, name = "Phoenix", isFavorite = false,
                    country = TODO(),
                    coordinates = TODO()
                )
            )
        )
    }

    override suspend fun loadCities() {
    }

    override fun searchCities(prefix: String, onlyFavorites: Boolean): Flow<List<City>> = flow {
        val results = if (onlyFavorites) {
            fakeCities.filter { it.name.lowercase().startsWith(prefix.lowercase()) && it.isFavorite }
        } else {
            fakeCities.filter { it.name.lowercase().startsWith(prefix.lowercase()) }
        }
        emit(results)
    }



    override suspend fun getFavoriteCities(): List<City> {
        return fakeCities.filter { it.isFavorite }
    }
}