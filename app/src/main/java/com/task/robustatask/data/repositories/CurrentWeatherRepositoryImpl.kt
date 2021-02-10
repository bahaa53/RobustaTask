package com.task.robustatask.data.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.task.robustatask.R
import com.task.robustatask.data.network.ApiInterface
import com.task.robustatask.data.network.apiClientLazy
import com.task.robustatask.domain.entities.CurrentWeatherResponse
import com.task.robustatask.domain.repositories.CurrentWeatherRepository
import com.task.robustatask.domain.repositories.Result
import com.task.robustatask.presentation.MyApp
import java.net.UnknownHostException

private const val CITY_NAME = "cairo"
private const val APPID = "2378558da77e8a07b782a026c8926888"
private const val unit = "metric"

class CurrentWeatherRepositoryImpl(
    private val context: Context = MyApp.applicationContext(),
    private val apiInterface: ApiInterface = apiClientLazy
) :
    CurrentWeatherRepository {

    private var _currentWeatherResponseResponseResult =
        MutableLiveData<Result<CurrentWeatherResponse>>()

    override val currentWeatherResponseResponseResult: LiveData<Result<CurrentWeatherResponse>>
        get() = _currentWeatherResponseResponseResult


    override suspend fun getCurrentWeather(): LiveData<Result<CurrentWeatherResponse>> {
        try {
            val currentWeatherResponse =
                apiInterface.getCurrentWeather(CITY_NAME, APPID, unit).await()
            if (currentWeatherResponse.isSuccessful) {

                _currentWeatherResponseResponseResult.value =
                    Result.Success(
                        currentWeatherResponse.body()
                    )

            } else {
                _currentWeatherResponseResponseResult.value =
                    Result.Error(message = context.getString(R.string.error_occured))
            }


        } catch (e: UnknownHostException) {
            _currentWeatherResponseResponseResult.value =
                Result.Error(
                    e,
                    message = context.getString(R.string.no_internet_connection)
                )

        } catch (e: Exception) {
            _currentWeatherResponseResponseResult.value = Result.Error(e)
        }
        return currentWeatherResponseResponseResult
    }
}