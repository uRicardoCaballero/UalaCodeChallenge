package com.example.ualacitieschallenge.data.model

data class City(
    val country: String,
    val name: String,
    val id: Long,
    val coord: Coordinates,
    var isFavorite: Boolean = false
) {
    fun displayName() = "$name, $country"
}

data class Coordinates(
    val lon: Double,
    val lat: Double
)