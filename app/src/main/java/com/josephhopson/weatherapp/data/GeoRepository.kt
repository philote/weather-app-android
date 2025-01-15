package com.josephhopson.weatherapp.data

import com.josephhopson.weatherapp.model.GeoData
import com.josephhopson.weatherapp.data.network.GeocodingApi

/**
 * Geocoding repository that fetches Latitude and Longitude from a zip code,
 * using the openweathermap API
 * Limitation: their is currently no way to add the country code so it may not work out of the US
 */
interface GeoRepository {
    suspend fun getLatLong(zipCode: String): GeoData
}

/**
 * Network Implementation of Repository fetches geo data
 * from zip codes, using the openweathermap API
 */
class NetworkGeoDataRepository() : GeoRepository {

    /**
     * Get latitude & longitude from a Zip code
     * @param zipCode User supplied zip code
     * @return GeoData object
     */
    override suspend fun getLatLong(zipCode: String): GeoData {
        return GeocodingApi.retrofitService.getLatLong(zipCode)
    }
}