package com.example.ualacitieschallenge.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Entity(tableName = "cities")
data class City(
    val country: String,
    val name: String,
    @PrimaryKey val id: Long,
    @TypeConverters(CoordinatesConverter::class) val coord: Coordinates,
    var isFavorite: Boolean = false
) {
    fun displayName() = "$name, $country".lowercase()
}

data class Coordinates(
    val lon: Double,
    val lat: Double
)

class CoordinatesConverter {
    @TypeConverter
    fun fromCoordinates(coordinates: Coordinates): String {
        return "${coordinates.lat},${coordinates.lon}"
    }

    @TypeConverter
    fun toCoordinates(value: String): Coordinates {
        val (lat, lon) = value.split(",")
        return Coordinates(lon.toDouble(), lat.toDouble())
    }
}