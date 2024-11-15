package com.josephhopson.weatherapp.model

import kotlinx.serialization.Serializable

/**
 * API https://openweathermap.org/api/geocoding-api
 * Object used in the serialization of geo json from the api
 */
@Serializable
data class GeoData(
    val zip: String,
    val name: String,
    val lat: Float,
    val lon: Float,
    val country: String
)
