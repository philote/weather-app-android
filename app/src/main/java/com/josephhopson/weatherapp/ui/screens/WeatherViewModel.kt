package com.josephhopson.weatherapp.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josephhopson.weatherapp.data.NetworkWeatherDataRepository
import com.josephhopson.weatherapp.data.WeatherApiResult
import com.josephhopson.weatherapp.model.FiveDayForecast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * UI state for the home screen
 * @property weatherUiState initialized as Landing
 */
data class AppUIState(
    val weatherUiState: WeatherUiState = WeatherUiState.Landing
)

/**
 * Weather ui state
 * 4 possible states the homescreen UI: Success with data, Error, Loading, Landing.
 * Landing is what is used to represent a fresh UI State, like when the app is started.
 */
sealed interface WeatherUiState {
    data class Success(val fiveDayForecast: FiveDayForecast) : WeatherUiState
    data object Error : WeatherUiState
    data object Loading : WeatherUiState
    data object Landing : WeatherUiState
}

class WeatherViewModel: ViewModel() {

    // backing properties to avoid state updates from other classes
    private val _uiState = MutableStateFlow(AppUIState())
    val uiState: StateFlow<AppUIState> = _uiState.asStateFlow()

    /** The mutable State that stores the status of the most recent user input for zip code */
    var userZipCode by mutableStateOf("")
        private set

    /**
     * Update user zip code
     * @param zipCode
     */
    fun updateUserZipCode(zipCode: String) {
        userZipCode = zipCode
    }

    /**
     * Public function to get weather data
     * Set the UI state to loading
     * Create a viewModelScope to do work
     * Start the process of converting the zip code into
     *  lat/long and then getting the 5 day forecast
     */
    fun getWeatherData() {
        _uiState.value = AppUIState(
            weatherUiState = WeatherUiState.Loading
        )
        viewModelScope.launch {
            _uiState.value = AppUIState(
                weatherUiState = getWeatherDataFromRepo()
            )
        }
    }

    /**
     * Private function to get weather data from repo layer
     * Requests weather data from the Weather repo with the user supplied zip code
     * Converts the repo api state into ui state, passing along the data in a success state
     * @return WeatherUiState
     */
    private suspend fun getWeatherDataFromRepo(): WeatherUiState {
        val weatherRepository = NetworkWeatherDataRepository()

        return when(
            val weatherApiResult = weatherRepository.getWeatherForecast(userZipCode)
        ) {
            is WeatherApiResult.Success -> {
                WeatherUiState.Success(weatherApiResult.fiveDayForecast)
            }
            WeatherApiResult.Error -> WeatherUiState.Error
        }
    }
}
