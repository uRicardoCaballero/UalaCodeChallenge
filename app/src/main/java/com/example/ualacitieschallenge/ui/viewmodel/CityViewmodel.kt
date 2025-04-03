package com.example.ualacitieschallenge.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ualacitieschallenge.data.model.City
import com.example.ualacitieschallenge.data.repository.CityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel


@HiltViewModel
class CityViewModel @Inject constructor(
    private val repository: CityRepository
) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    private val _showOnlyFavorites = MutableStateFlow(false)
    private val _selectedCity = MutableStateFlow<City?>(null)
    private val _isLoading = MutableStateFlow(false)

    val cities = _searchQuery
        .debounce(300)
        .flatMapLatest { query ->
            repository.searchCities(query, _showOnlyFavorites.value)
        }

    val isLoading: StateFlow<Boolean> = _isLoading
    val selectedCity: StateFlow<City?> = _selectedCity
    val showOnlyFavorites: StateFlow<Boolean> = _showOnlyFavorites

    init {
        viewModelScope.launch {
            _isLoading.value = true
            repository.loadCities()
            _isLoading.value = false
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onToggleFavorite(city: City) {
        viewModelScope.launch {
            repository.toggleFavorite(city.id, !city.isFavorite)
        }
    }

    fun onCitySelected(city: City) {
        _selectedCity.value = city
    }

    fun onShowOnlyFavoritesChanged(showOnly: Boolean) {
        _showOnlyFavorites.value = showOnly
    }

    fun clearSelectedCity() {
        _selectedCity.value = null
    }
}