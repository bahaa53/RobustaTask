package com.task.robustatask.presentation.weatherStory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.task.robustatask.domain.usecase.WeatherUseCase

class WeatherViewModelFactory(private val useCase: WeatherUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(
                useCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}