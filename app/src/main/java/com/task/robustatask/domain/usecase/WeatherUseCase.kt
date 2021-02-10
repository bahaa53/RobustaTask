package com.task.robustatask.domain.usecase

import androidx.lifecycle.LiveData
import com.task.robustatask.domain.entities.CurrentWeatherResponse
import com.task.robustatask.domain.repositories.CurrentWeatherRepository
import com.task.robustatask.domain.repositories.Result
import com.task.robustatask.domain.repositories.currentWeatherRepositoryLazy

class WeatherUseCase(private val currentWeatherRepository: CurrentWeatherRepository = currentWeatherRepositoryLazy) {
    suspend fun getCurrentWeather(): LiveData<Result<CurrentWeatherResponse>> {
        return currentWeatherRepository.getCurrentWeather()
    }
}