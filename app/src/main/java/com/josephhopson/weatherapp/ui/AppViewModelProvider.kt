package com.josephhopson.weatherapp.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.josephhopson.weatherapp.ui.screens.WeatherViewModel
import com.josephhopson.weatherapp.WeatherAppApplication

/**
 * Factory for app view models
 */
object AppViewModelProvider {
    // Initializer for WeatherViewModel
    val Factory = viewModelFactory {
        initializer {
            WeatherViewModel(
                weatherAppApplication().container.weatherRepository
            )
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [WeatherAppApplication].
 */
fun CreationExtras.weatherAppApplication(): WeatherAppApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as WeatherAppApplication)