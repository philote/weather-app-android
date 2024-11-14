package com.josephhopson.weatherapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//
/**
 * API: https://openweathermap.org/forecast5
 * The API doesn't make it clear what parameters are
 * optionally returned, so I created some defaults.
 */
@Serializable
data class FiveDayForecast(
    var cod: String = "",
    var message: Int = -1,
    var cnt: Int = -1,
    @SerialName("list") var forecasts: ArrayList<Forecast> = arrayListOf(),
    var city: City = City()
)

// List of forecasts
@Serializable
data class Forecast(
    var dt: Int = -1,
    var main: Main = Main(),
    var weather: ArrayList<Weather> = arrayListOf(),
    var clouds: Clouds = Clouds(),
    var wind: Wind = Wind(),
    var visibility: Int = -1,
    var pop: Int = -1,
    var sys: Sys = Sys(),
    @SerialName("dt_txt") var dtTxt: String = ""
)

@Serializable
data class Main(
    var temp: Float = -1F,
    @SerialName("feels_like") var feelsLike: Float = -1F,
    @SerialName("temp_min") var tempMin: Float = -1F,
    @SerialName("temp_max") var tempMax: Float = -1F,
    var pressure: Int = -1,
    @SerialName("sea_level") var seaLevel: Int = -1,
    @SerialName("grnd_level") var grndLevel: Int = -1,
    var humidity: Int = -1,
    @SerialName("temp_kf") var tempKf: Float = -1F
)

@Serializable
data class Weather(
    var id: Int = -1,
    var main: String = "",
    var description: String = "",
    var icon: String = ""
)

@Serializable data class Clouds( var all: Int = -1)

@Serializable
data class Wind(
    var speed: Float = -1F,
    var deg: Int = -1,
    var gust: Float = -1F
)

@Serializable data class Sys( var pod: String = "")

// City of Forecast
@Serializable
data class City(
    var id: Int = -1,
    var name: String = "",
    var coord: Coord = Coord(),
    var country: String = "",
    var population: Int = -1,
    var timezone: Int = -1,
    var sunrise: Int = -1,
    var sunset: Int = -1
)

@Serializable
data class Coord(
    var lat: Float = -1F,
    var lon: Float = -1F
)