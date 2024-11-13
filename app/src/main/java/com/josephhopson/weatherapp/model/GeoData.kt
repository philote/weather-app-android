package com.josephhopson.weatherapp.model

import kotlinx.serialization.Serializable

@Serializable
data class GeoData(
    val zip: String,
    val name: String,
    val lat: Float,
    val lon: Float,
    val country: String
)
