package com.example.ualacitieschallenge.data.model



data class City(
    val id: Long,               // Unique ID for the city (from the API)
    val name: String,           // Name of the city
    val country: String,        // Country code (e.g., "UA", "RU")
    val coordinates: Coordinates, // Coordinates of the city (latitude and longitude)
    var isFavorite: Boolean     // Favorite status of the city (true/false)
)

data class Coordinates(
    val longitude: Double,      // Longitude of the city
    val latitude: Double        // Latitude of the city
)