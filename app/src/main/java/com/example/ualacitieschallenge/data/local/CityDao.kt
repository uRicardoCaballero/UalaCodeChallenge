package com.example.ualacitieschallenge.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ualacitieschallenge.data.model.City

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cities: List<City>)

    @Query("SELECT * FROM cities WHERE isFavorite = 1")
    suspend fun getFavoriteCities(): List<City>

    @Query("UPDATE cities SET isFavorite = :isFavorite WHERE id = :cityId")
    suspend fun updateFavoriteStatus(cityId: Long, isFavorite: Boolean)

    @Query("SELECT * FROM cities WHERE LOWER(name || ', ' || country) LIKE :prefix || '%' ORDER BY name, country")
    suspend fun searchCities(prefix: String): List<City>

    @Query("SELECT * FROM cities WHERE isFavorite = 1 AND LOWER(name || ', ' || country) LIKE :prefix || '%' ORDER BY name, country")
    suspend fun searchFavoriteCities(prefix: String): List<City>
}