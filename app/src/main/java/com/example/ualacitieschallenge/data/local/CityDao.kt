package com.example.ualacitieschallenge.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ualacitieschallenge.data.model.CityEntity

@Dao
interface CityDao {
    @Query("SELECT * FROM cities WHERE name LIKE :prefix || '%'")
    fun searchCities(prefix: String): List<CityEntity>

    @Query("SELECT * FROM cities WHERE name LIKE :prefix || '%' AND isFavorite = 1")
    fun searchFavoriteCities(prefix: String): List<CityEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(cities: List<CityEntity>)

    @Query("UPDATE cities SET isFavorite = :isFavorite WHERE id = :cityId")
    fun updateFavoriteStatus(cityId: String, isFavorite: Boolean)

    @Query("SELECT * FROM cities WHERE isFavorite = 1")
    fun getFavoriteCities(): List<CityEntity>
}