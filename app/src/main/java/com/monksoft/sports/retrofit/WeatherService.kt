package com.monksoft.sports.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("data/2.5/weather")
    suspend fun getWeatherById(
        @Query("id") lon: Long,
        @Query("units") units: String?,
        @Query("appid") appid: String?): WeatherEntity
}