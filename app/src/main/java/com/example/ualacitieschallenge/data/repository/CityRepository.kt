package com.example.ualacitieschallenge.data.repository

import com.example.ualacitieschallenge.data.local.CityDao
import com.example.ualacitieschallenge.data.model.City
import com.example.ualacitieschallenge.data.remote.CityApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class CityRepository @Inject constructor(
    private val cityApi: CityApi,
    private val cityDao: CityDao
) {
    private val citiesGistUrl = "https://gist.githubusercontent.com/hernan-uala/dce8843a8edbe0b0018b32e137bc2b3a/raw/0996accf70cb0ca0e16f9a99e0ee185fafca7af1/cities.json"
    suspend fun loadCities() {
        try {
            val response = cityApi.getCities(citiesGistUrl)
            if (response.isSuccessful) {
                response.body()?.let { cities ->
                    cityDao.insertAll(cities)
                }
            } else {
                throw Exception("Failed to download cities: ${response.code()}")
            }
        } catch (e: Exception) {
            throw Exception("Network error: ${e.message}")
        }
    }

    fun searchCities(prefix: String, onlyFavorites: Boolean): Flow<List<City>> = flow {
        val results = if (onlyFavorites) {
            cityDao.searchFavoriteCities(prefix.lowercase())
        } else {
            cityDao.searchCities(prefix.lowercase())
        }
        emit(results)
    }

    suspend fun toggleFavorite(cityId: Long, isFavorite: Boolean) {
        cityDao.updateFavoriteStatus(cityId, isFavorite)
    }

    suspend fun getFavoriteCities(): List<City> {
        return cityDao.getFavoriteCities()
    }
}