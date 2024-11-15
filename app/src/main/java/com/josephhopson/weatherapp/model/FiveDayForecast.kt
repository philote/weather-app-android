package com.josephhopson.weatherapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * API: https://openweathermap.org/forecast5
 * Object used in the serialization of 5 day forecast json from the api
 * NOTE: The API doesn't make it clear what parameters are
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

@Parcelize
@Serializable
data class Forecast(
    var dt: Int = -1,
    var main: Main = Main(),
    var weather: ArrayList<Weather> = arrayListOf(),
    var clouds: Clouds = Clouds(),
    var wind: Wind = Wind(),
    var visibility: Int = -1,
    var pop: Double = -1.0,
    var rain: Rain = Rain(),
    var snow: Snow = Snow(),
    var sys: Sys = Sys(),
    @SerialName("dt_txt") var dtTxt: String = ""
) : Parcelable

@Parcelize
@Serializable
data class Main(
    var temp: Double = -1.0,
    @SerialName("feels_like") var feelsLike: Double = -1.0,
    @SerialName("temp_min") var tempMin: Double = -1.0,
    @SerialName("temp_max") var tempMax: Double = -1.0,
    var pressure: Int = -1,
    @SerialName("sea_level") var seaLevel: Int = -1,
    @SerialName("grnd_level") var grndLevel: Int = -1,
    var humidity: Int = -1,
    @SerialName("temp_kf") var tempKf: Double = -1.0
) : Parcelable

@Parcelize
@Serializable
data class Weather(
    var id: Int = -1,
    var main: String = "",
    var description: String = "",
    var icon: String = ""
) : Parcelable

@Parcelize
@Serializable
data class Clouds( var all: Int = -1) : Parcelable

@Parcelize
@Serializable
data class Wind(
    var speed: Double = -1.0,
    var deg: Int = -1,
    var gust: Double = -1.0
) : Parcelable

@Parcelize
@Serializable
data class Sys( var pod: String = "") : Parcelable

@Parcelize
@Serializable
data class Rain(
    @SerialName("3h") var threeH: Double = -1.0
) : Parcelable

@Parcelize
@Serializable
data class Snow(
    @SerialName("3h") var threeH: Double = -1.0
) : Parcelable

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
    var lat: Double = -1.0,
    var lon: Double = -1.0
)
