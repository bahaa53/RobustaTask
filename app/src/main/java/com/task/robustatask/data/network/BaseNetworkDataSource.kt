package com.task.robustatask.data.network

import retrofit2.Response

interface BaseNetworkDataSource {
    suspend fun <T : Any> checkIfValidRespons(baseResponse: Response<T>)
}