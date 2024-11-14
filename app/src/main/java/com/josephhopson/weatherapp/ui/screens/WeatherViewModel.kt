package com.josephhopson.weatherapp.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josephhopson.weatherapp.data.NetworkGeoDataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

data class AppUIState(
//    val currentZipCode: String = ""
    val weatherApiUiState: WeatherApiUiState = WeatherApiUiState.Loading
)
sealed interface WeatherApiUiState {
    data class Success(val weather: String) : WeatherApiUiState
    data object Error : WeatherApiUiState
    data object Loading : WeatherApiUiState
}

class WeatherViewModel: ViewModel() {

    // backing prop to avoid state updates from other classes
    private val _uiState = MutableStateFlow(AppUIState())
    val uiState: StateFlow<AppUIState> = _uiState.asStateFlow()
    var userZipCode by mutableStateOf("")
        private set

    init {
        getWeatherData()
    }

    fun updateUserZipCode(zipCode: String) {
        userZipCode = zipCode
    }

    fun getWeatherData() {
        // TODO Clear the current data
        viewModelScope.launch {
            _uiState.value = AppUIState(
                weatherApiUiState = getWeatherDataFromRepo()
            )
        }
    }

    private suspend fun getWeatherDataFromRepo(): WeatherApiUiState {
        return try {
            val geoRepository = NetworkGeoDataRepository()
            val result = geoRepository.getLatLong(userZipCode)
            WeatherApiUiState.Success(result.toString())
        } catch (e: IOException) {
            Log.e("RETROFIT:IOException", e.message.toString())
            WeatherApiUiState.Error
        } catch (e: HttpException) {
            Log.e("RETROFIT:HttpException", e.message.toString())
            WeatherApiUiState.Error
        }
    }
}