package com.josephhopson.weatherapp.data

import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val weatherRepository: WeatherRepository
}

/**
 * [AppContainer] implementation that provides instance of [WeatherRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {

    /**
     * Implementation for [WeatherRepository]
     */
    override val weatherRepository: WeatherRepository by lazy {
        NetworkWeatherDataRepository()
    }
}