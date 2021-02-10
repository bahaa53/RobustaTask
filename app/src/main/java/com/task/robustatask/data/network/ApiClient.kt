package com.task.robustatask.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.task.robustatask.data.network.checkingConnectivity.ConnectivityInterceptor
import com.task.robustatask.data.network.checkingConnectivity.connectivityInterceptorLazy
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL: String = "http://api.openweathermap.org/"
private const val READ_TIME_OUT = 120L
private const val CONNECTION_TIME_OUT = 120L
private const val WRITE_TIME_OUT = 120L

val apiClientLazy by lazy {
    ApiClient(
        connectivityInterceptor = connectivityInterceptorLazy
    )
}

interface ApiClient {

    companion object {
        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor): ApiInterface {
            val requestInterceptor = Interceptor { chain ->

                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("Content-Type", "application/json")
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(interceptor)
                .addInterceptor(connectivityInterceptor)
                .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface::class.java)
        }
    }
}