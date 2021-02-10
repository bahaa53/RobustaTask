package com.task.robustatask.presentation.weatherStory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.task.robustatask.R
import com.task.robustatask.data.core.BASE_IMAGE_URL
import com.task.robustatask.domain.entities.CurrentWeatherResponse
import com.task.robustatask.domain.repositories.Result
import com.task.robustatask.presentation.CustomProgressBar
import com.task.robustatask.presentation.MyApp
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.weather_card.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), KodeinAware, CoroutineScope {

    /////////////////// init Variables /////////////////////////////////
    override val kodein: Kodein by closestKodein(MyApp.applicationContext())

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job

    private val progressDialog: CustomProgressBar? = CustomProgressBar()

    /////////////////Declaring View Model //////////////////////////////////////////
    private val weatherViewModelFactory: WeatherViewModelFactory by instance()
    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        job = Job()
        setupViewModel()
        progressDialog?.show(this)
        getCurrentWeather()

    }

    private fun setupViewModel() {
        weatherViewModel = ViewModelProvider(this, weatherViewModelFactory)
            .get(WeatherViewModel::class.java)
    }

    private fun getCurrentWeather() = launch {
        val currentWeatherResponse = weatherViewModel.getCurrentWeather()
        observeForGettingCurrentWeather(currentWeatherResponse)
    }

    private fun observeForGettingCurrentWeather(forgetPasswordResponse: LiveData<Result<CurrentWeatherResponse>>) {


        forgetPasswordResponse.observe(
            this@MainActivity,
            Observer<Result<CurrentWeatherResponse>> {
                when (it) {
                    is Result.Success<CurrentWeatherResponse> -> {
                        progressDialog?.dismiss()
                        updateUI(it?.data)
                    }
                    is Result.Error -> {
                        progressDialog?.dismiss()

                    }


                }

            })
    }

    private fun updateUI(data: CurrentWeatherResponse?) {
        val weathObj = data?.weather?.get(0)
        val windObj = data?.wind
        val mainObj = data?.main
        val imageUrl = "${BASE_IMAGE_URL}${weathObj?.icon}@2x.png"
        textViewCardCityName.text = data?.name ?: ""
        textViewCardWeatherDescription.text = weathObj?.description ?: ""
        Picasso.get().load(imageUrl).into(imageViewCardWeatherIcon)
        textViewCardCurrentTemp.text = "${mainObj?.temp?.toInt()} ${getString(R.string.degree)}"
        textViewCardMaxTemp.text = "${mainObj?.tempMax?.toInt()} ${getString(R.string.degree)}"
        textViewCardMinTemp.text = "${mainObj?.tempMin?.toInt()} ${getString(R.string.degree)}"
        textViewHumidity.text = "${mainObj?.humidity} ${getString(R.string.percentage)}"
        textViewWind.text = "${windObj?.speed} ${getString(R.string.speed)}"
        textViewCloudiness.text = "${windObj?.deg} ${getString(R.string.percentage)}"
        textViewPressure.text = "${mainObj?.pressure} ${getString(R.string.hPa)}"
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}
