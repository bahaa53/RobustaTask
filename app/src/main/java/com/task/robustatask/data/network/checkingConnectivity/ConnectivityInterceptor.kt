package com.task.robustatask.data.network.checkingConnectivity
import com.task.robustatask.presentation.MyApp
import okhttp3.Interceptor

val connectivityInterceptorLazy by lazy { ConnectivityInterceptorImpl(context = MyApp.applicationContext()) }

interface ConnectivityInterceptor : Interceptor{
}