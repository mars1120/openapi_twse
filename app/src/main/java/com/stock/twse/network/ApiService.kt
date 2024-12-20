package com.stock.twse.network

import com.stock.twse.data.BwibbuAll

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {

    @Headers("Content-type: application/json", "Accept: application/json")
    @GET("v1/exchangeReport/BWIBBU_ALL")
    fun getBwibbuAll(
    ): Call<BwibbuAll>
}