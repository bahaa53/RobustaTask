package com.task.robustatask.data.network


import com.task.robustatask.data.core.CURRENT_WEATHER_URL
import com.task.robustatask.domain.entities.CurrentWeatherResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiInterface {

    @GET(CURRENT_WEATHER_URL)
    fun getCurrentWeather(
        @Query("q") q: String,
        @Query("appid") appid: String,
        @Query("units") msisdn: String
    ): Deferred<Response<CurrentWeatherResponse>>
}