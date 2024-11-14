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

data class AppUIState(
//    val currentZipCode: String = ""
    val weatherUiState: WeatherUiState = WeatherUiState.Landing
)
sealed interface WeatherUiState {
    data class Success(val fiveDayForecast: FiveDayForecast) : WeatherUiState
    data object Error : WeatherUiState
    data object Loading : WeatherUiState
    data object Landing : WeatherUiState
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
        _uiState.value = AppUIState(
            weatherUiState = WeatherUiState.Loading
        )
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
                WeatherUiState.Success(weatherApiResult.fiveDayForecast)
            }
            WeatherApiResult.Error -> WeatherUiState.Error
        }
    }
}