package com.josephhopson.weatherapp

import android.app.Application
import com.josephhopson.weatherapp.data.AppContainer
import com.josephhopson.weatherapp.data.AppDataContainer

class WeatherAppApplication : Application() {
    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}