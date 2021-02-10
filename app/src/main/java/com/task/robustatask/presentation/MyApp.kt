package com.task.robustatask.presentation

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.task.robustatask.domain.usecase.WeatherUseCase
import com.task.robustatask.presentation.weatherStory.WeatherViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

class MyApp : MultiDexApplication(), KodeinAware {

    init {
        instance = this
    }

    override val kodein = Kodein.lazy {

        import(androidXModule(this@MyApp))
        /////////////////////////Inject Login  USE CASE /////////////////////////////
        bind() from provider { WeatherUseCase() }
        ///////////////Inject Viewmodel Factory ////////////////////////////
        bind() from provider {
            WeatherViewModelFactory(
                instance()
            )
        }
    }

    companion object {
        private var instance: MyApp? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}