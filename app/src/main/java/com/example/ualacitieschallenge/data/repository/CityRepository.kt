package com.example.ualacitieschallenge.data.repository

import com.example.ualacitieschallenge.data.local.CityDao
import com.example.ualacitieschallenge.data.model.City
import com.example.ualacitieschallenge.data.model.CityEntity
import com.example.ualacitieschallenge.data.model.Coordinates
import com.example.ualacitieschallenge.data.remote.CityApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

open class CityRepository @Inject constructor(
    private val cityApi: CityApi?,
    private val cityDao: CityDao?
) {
    private val citiesGistUrl = "https://gist.githubusercontent.com/hernan-uala/dce8843a8edbe0b0018b32e137bc2b3a/raw/0996accf70cb0ca0e16f9a99e0ee185fafca7af1/cities.json"

    open suspend fun loadCities() {
        val response = cityApi?.getCities(citiesGistUrl)
        if (response != null) {
            if (response.isSuccessful) {
                response.body()?.let { cities ->
                    val cityEntities = cities.map { city ->
                        CityEntity(
                            id = city.id,
                            name = city.name,
                            isFavorite = false // Default to not favorite
                        )
                    }
                    cityDao?.insertAll(cityEntities)
                }
            }
        }
    }

    open fun searchCities(prefix: String, onlyFavorites: Boolean): Flow<List<City>> = flow {
        val results = if (onlyFavorites) {
            cityDao?.searchFavoriteCities(prefix.lowercase())
        } else {
            cityDao?.searchCities(prefix.lowercase())
        }
        results?.map { entity ->
            City(
                id = entity.id,
                name = entity.name,
                country = "",
                coordinates = Coordinates(0.0, 0.0),
                isFavorite = entity.isFavorite
            )
        }?.let { emit(it) }
    }

    suspend fun toggleFavorite(cityId: String, isFavorite: Boolean) {
        cityDao?.updateFavoriteStatus(cityId, isFavorite)
    }

    open suspend fun getFavoriteCities(): List<City> {
        return cityDao?.getFavoriteCities()?.map { entity ->
            City(
                id = entity.id,
                name = entity.name,
                country = "", // Not available in CityEntity
                coordinates = Coordinates(0.0, 0.0), // Not available in CityEntity
                isFavorite = entity.isFavorite
            )
        } ?:return emptyList()
    }
}