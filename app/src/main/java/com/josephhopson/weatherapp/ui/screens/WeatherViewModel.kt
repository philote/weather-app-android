package com.josephhopson.weatherapp.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josephhopson.weatherapp.data.NetworkWeatherDataRepository
import com.josephhopson.weatherapp.data.WeatherApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AppUIState(
//    val currentZipCode: String = ""
    val weatherUiState: WeatherUiState = WeatherUiState.Loading
)
sealed interface WeatherUiState {
    data class Success(val weather: String) : WeatherUiState
    data object Error : WeatherUiState
    data object Loading : WeatherUiState
}

class WeatherViewModel: ViewModel() {

    // backing prop to avoid state updates from other classes
    private val _uiState = MutableStateFlow(AppUIState())
    val uiState: StateFlow<AppUIState> = _uiState.asStateFlow()
    var userZipCode by mutableStateOf("")
        private set

    fun updateUserZipCode(zipCode: String) {
        userZipCode = zipCode
    }

    fun getWeatherData() {
        // TODO Clear the current data
        viewModelScope.launch {
            _uiState.value = AppUIState(
                weatherUiState = getWeatherDataFromRepo()
            )
        }
    }

    private suspend fun getWeatherDataFromRepo(): WeatherUiState {
        val weatherRepository = NetworkWeatherDataRepository()

        return when(
            val weatherApiResult = weatherRepository.getWeatherForecast(userZipCode)
        ) {
            is WeatherApiResult.Success -> {
                WeatherUiState.Success(weatherApiResult.forecasts.toString())
            }
            WeatherApiResult.Error -> WeatherUiState.Error
        }
    }
}