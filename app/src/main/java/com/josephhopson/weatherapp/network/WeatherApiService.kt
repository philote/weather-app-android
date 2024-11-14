package com.josephhopson.weatherapp.network

import com.josephhopson.weatherapp.model.FiveDayForecast
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Example https://api.openweathermap.org/data/2.5/forecast?lat={lat}&lon={lon}&appid={API key}
private const val BASE_WEATHER_URL = "https://api.openweathermap.org/data/2.5/"
private const val APP_ID = "b6e35ee5a97a0287df777b3c1955b44b"

private val retroJson = Json { ignoreUnknownKeys = true }
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_WEATHER_URL)
    .addConverterFactory(retroJson.asConverterFactory("application/json; charset=UTF8".toMediaType()))
    .build()

interface WeatherApiService {
    @GET("forecast?appid=$APP_ID")
    suspend fun getForecast(@Query("lat") latitude: Float, @Query("lon") longitude: Float): FiveDayForecast
}

object WeatherApi {
    val retrofitService : WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}