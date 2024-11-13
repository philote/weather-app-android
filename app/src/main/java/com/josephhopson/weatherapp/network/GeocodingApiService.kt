package com.josephhopson.weatherapp.network

import com.josephhopson.weatherapp.model.GeoData
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Example https://api.openweathermap.org/geo/1.0/zip?zip={zip code},{country code}&appid={API key}
private const val BASE_GEO_URL = "https://api.openweathermap.org/geo/1.0/"
private const val APP_ID = "b6e35ee5a97a0287df777b3c1955b44b"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_GEO_URL)
    .addConverterFactory(Json.asConverterFactory("application/json; charset=UTF8".toMediaType()))
    .build()

interface GeocodingApiService {
    @GET("zip?appid=$APP_ID")
    suspend fun getLatLong(@Query("zip") zip: String): GeoData
}

object GeocodingApi {
    val retrofitService : GeocodingApiService by lazy {
        retrofit.create(GeocodingApiService::class.java)
    }
}