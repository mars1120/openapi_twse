package com.stock.twse.network

import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

object NetworkRequest {

    /**
     * create service
     */
    private val service = ServiceCreator.create(ApiService::class.java)

    suspend fun getBwibbuAll() = service.getBwibbuAll().await()

    suspend fun getStockDayAvgAll() = service.getStockDayAvgAll().await()

    suspend fun getStockDayAll() = service.getStockDayAll().await()
    private suspend fun <T> Call<T>.await(): T = suspendCancellableCoroutine { continuation ->
        continuation.invokeOnCancellation {
            cancel()
        }

        enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()
                if (body != null) continuation.resume(body)
                else continuation.resumeWithException(RuntimeException("response body is null"))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                continuation.resumeWithException(t)
            }
        })
    }
}

