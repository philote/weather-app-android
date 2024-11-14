package com.josephhopson.weatherapp.data

import android.util.Log
import com.josephhopson.weatherapp.model.Forecast
import com.josephhopson.weatherapp.network.WeatherApi
import retrofit2.HttpException
import java.io.IOException

sealed interface WeatherApiResult {
    data class Success(val forecasts: List<Forecast>) : WeatherApiResult
    data object Error : WeatherApiResult
}

interface WeatherRepository {
    suspend fun getWeatherForecast(zipCode: String): WeatherApiResult
}

class NetworkWeatherDataRepository() : WeatherRepository {

    private val geoRepository = NetworkGeoDataRepository()

    override suspend fun getWeatherForecast(zipCode: String): WeatherApiResult {
        return try {
            val result = geoRepository.getLatLong(zipCode)
            Log.d("Geo", result.toString())
            getWeatherForecast(result.lat, result.lon)
        } catch (e: IOException) {
            Log.e("Geo:IOException", e.message.toString())
            WeatherApiResult.Error
        } catch (e: HttpException) {
            Log.e("Geo:HttpException", e.message.toString())
            WeatherApiResult.Error
        }
    }

    private suspend fun getWeatherForecast(latitude: Float, longitude: Float): WeatherApiResult {
        return try {
            val result = WeatherApi.retrofitService.getForecast(latitude, longitude)
            Log.d("Forecast", result.toString())
            Log.d("Forecast List", result.forecasts.toString())
            WeatherApiResult.Success(result.forecasts)
        } catch (e: IOException) {
            Log.e("Forecast:IOException", e.message.toString())
            WeatherApiResult.Error
        } catch (e: HttpException) {
            Log.e("Forecast:HttpException", e.message.toString())
            WeatherApiResult.Error
        }
    }

}