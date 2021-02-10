package com.task.robustatask.domain.repositories

import androidx.lifecycle.LiveData
import com.task.robustatask.data.repositories.CurrentWeatherRepositoryImpl
import com.task.robustatask.domain.entities.CurrentWeatherResponse

val currentWeatherRepositoryLazy by lazy { CurrentWeatherRepositoryImpl() }

interface CurrentWeatherRepository {

    val currentWeatherResponseResponseResult: LiveData<Result<CurrentWeatherResponse>>

    suspend fun getCurrentWeather(): LiveData<Result<CurrentWeatherResponse>>
}