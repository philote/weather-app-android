package com.josephhopson.weatherapp.data

import android.util.Log
import com.josephhopson.weatherapp.model.FiveDayForecast
import com.josephhopson.weatherapp.data.network.WeatherApi
import retrofit2.HttpException
import java.io.IOException

/**
 * Weather api result: Success with data or Error
 */
sealed interface WeatherApiResult {
    data class Success(val fiveDayForecast: FiveDayForecast) : WeatherApiResult
    data object Error : WeatherApiResult
}

/**
 * Weather repository fetches the 5 day forecast
 * using the openweathermap API
 * @returns WeatherApiResult
 */
interface WeatherRepository {
    suspend fun getWeatherForecast(zipCode: String): WeatherApiResult
}

/**
 * Network Implementation of Repository fetches the 5 day forecast
 * from zip codes, using the openweathermap API
 */
class NetworkWeatherDataRepository() : WeatherRepository {

    private val geoRepository = NetworkGeoDataRepository()

    /**
     * Get weather forecast: 1st get the lat long from a zip code then the 5 day forecast
     * using the resulting lat/long
     * @param zipCode
     * @return WeatherApiResult Wrapped response or generic error
     */
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

    /**
     * Get weather forecast get the 5 day forecast using lat/long
     *
     * @param latitude
     * @param longitude
     * @return WeatherApiResult Wrapped response or generic error
     */
    private suspend fun getWeatherForecast(latitude: Float, longitude: Float): WeatherApiResult {
        return try {
            val result = WeatherApi.retrofitService.getForecast(latitude, longitude)
            Log.d("Forecast", result.toString())
            WeatherApiResult.Success(result)
        } catch (e: IOException) {
            Log.e("Forecast:IOException", e.message.toString())
            WeatherApiResult.Error
        } catch (e: HttpException) {
            Log.e("Forecast:HttpException", e.message.toString())
            WeatherApiResult.Error
        }
    }

}