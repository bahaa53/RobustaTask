package com.task.robustatask.presentation.weatherStory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.task.robustatask.domain.entities.CurrentWeatherResponse
import com.task.robustatask.domain.repositories.Result
import com.task.robustatask.domain.usecase.WeatherUseCase

class WeatherViewModel(private val weatherUseCase: WeatherUseCase) : ViewModel() {

    suspend fun getCurrentWeather(): LiveData<Result<CurrentWeatherResponse>> {
        return weatherUseCase.getCurrentWeather()
    }
}
