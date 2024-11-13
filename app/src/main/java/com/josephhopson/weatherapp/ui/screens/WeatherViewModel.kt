package com.josephhopson.weatherapp.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josephhopson.weatherapp.data.NetworkGeoDataRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface WeatherUiState {
    data class Success(val weather: String) : WeatherUiState
    object Error : WeatherUiState
    object Loading : WeatherUiState
}

class WeatherViewModel: ViewModel() {

    var weatherUiState: WeatherUiState by mutableStateOf(WeatherUiState.Loading)
        private set

    init {
        getWeatherData()
    }

    fun getWeatherData() {
        viewModelScope.launch {
            weatherUiState = try {
                val geoRepository = NetworkGeoDataRepository()
                val result = geoRepository.getLatLong("80003")
                WeatherUiState.Success(result.toString())
            } catch (e: IOException) {
                Log.e("RETROFIT:IOException", e.message.toString())
                WeatherUiState.Error
            } catch (e: HttpException) {
                Log.e("RETROFIT:HttpException", e.message.toString())
                WeatherUiState.Error
            }
        }
    }
}