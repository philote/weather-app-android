package com.josephhopson.weatherapp.data.network

import com.josephhopson.weatherapp.model.GeoData
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_GEO_URL = "https://api.openweathermap.org/geo/1.0/"
private const val APP_ID = "b6e35ee5a97a0287df777b3c1955b44b"
// Don't blow up if the API adds extra stuff
private val retroJson = Json { ignoreUnknownKeys = true }
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_GEO_URL)
    .addConverterFactory(retroJson.asConverterFactory("application/json; charset=UTF8".toMediaType()))
    .build()

/**
 * Geocoding api service
 * Example: https://api.openweathermap.org/geo/1.0/zip?zip={zip code},{country code}&appid={API key}
 * A public interface that exposes the [getLatLong] method
 */
interface GeocodingApiService {
    @GET("zip?appid=$APP_ID")
    suspend fun getLatLong(@Query("zip") zip: String): GeoData
}

/**
 * Geocoding api: singleton with lazy initialization
 */
object GeocodingApi {
    val retrofitService : GeocodingApiService by lazy {
        retrofit.create(GeocodingApiService::class.java)
    }
}