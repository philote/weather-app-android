package com.josephhopson.weatherapp.data

import com.josephhopson.weatherapp.model.GeoData
import com.josephhopson.weatherapp.network.GeocodingApi

interface GeoRepository {
    suspend fun getLatLong(zipCode: String): GeoData
}

class NetworkGeoDataRepository() : GeoRepository {
    override suspend fun getLatLong(zipCode: String): GeoData {
        return GeocodingApi.retrofitService.getLatLong(zipCode)
    }
}