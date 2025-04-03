package com.example.ualacitieschallenge.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ualacitieschallenge.data.model.City
import com.example.ualacitieschallenge.data.remote.CityApi
import com.example.ualacitieschallenge.data.local.CityDao
import com.example.ualacitieschallenge.data.repository.CityRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CityViewModel(
    private val cityRepository: CityRepository,
    private val cityApi: CityApi,
    private val cityDao: CityDao
) : ViewModel() {

    // State for the list of cities
    private val _cities = MutableStateFlow<List<City>>(emptyList())
    val cities: StateFlow<List<City>> = _cities

    // State for the search query
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    // State for showing only favorite cities
    private val _showOnlyFavorites = MutableStateFlow(false)
    val showOnlyFavorites: StateFlow<Boolean> = _showOnlyFavorites

    // State for loading indicator
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // State for error messages
    private val _errorMessage = MutableSharedFlow<String?>()
    val errorMessage: SharedFlow<String?> = _errorMessage

    init {
        // Load cities when the ViewModel is initialized
        loadCities()
    }

    /**
     * Loads cities from the repository.
     */
    private fun loadCities() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                cityRepository.loadCities()
                // Fetch all cities after loading
                searchCities(_searchQuery.value, _showOnlyFavorites.value)
            } catch (e: Exception) {
                _errorMessage.emit(e.message ?: "An unknown error occurred")
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Searches for cities based on the query and favorite status.
     */
    fun searchCities(query: String, onlyFavorites: Boolean) {
        _searchQuery.value = query
        _showOnlyFavorites.value = onlyFavorites

        viewModelScope.launch {
            cityRepository.searchCities(query, onlyFavorites).collect { cities ->
                _cities.value = cities
            }
        }
    }

    /**
     * Toggles the favorite status of a city.
     */
    fun onToggleFavorite(city: City) {
        viewModelScope.launch {
            try {
                cityRepository.toggleFavorite(city.id.toString(), !city.isFavorite)
                // Refresh the list after toggling
                searchCities(_searchQuery.value, _showOnlyFavorites.value)
            } catch (e: Exception) {
                _errorMessage.emit(e.message ?: "Failed to update favorite status")
            }
        }
    }

    /**
     * Updates the search query.
     */
    fun onSearchQueryChanged(query: String) {
        searchCities(query, _showOnlyFavorites.value)
    }

    /**
     * Updates the showOnlyFavorites flag.
     */
    fun onShowOnlyFavoritesChanged(showOnlyFavorites: Boolean) {
        searchCities(_searchQuery.value, showOnlyFavorites)
    }

    /**
     * Get a city by its ID.
     */
    fun getCityById(cityId: Long): City? {
        return _cities.value.find { it.id == cityId }
    }
}