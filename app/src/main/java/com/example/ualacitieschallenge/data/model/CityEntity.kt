package com.example.ualacitieschallenge.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities")
data class CityEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val isFavorite: Boolean
)