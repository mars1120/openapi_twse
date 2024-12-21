package com.stock.twse.network

import StockDayAvgAll
import com.stock.twse.StockDayAll
import com.stock.twse.data.BwibbuAll

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {

    @Headers("Content-type: application/json", "Accept: application/json")
    @GET("v1/exchangeReport/STOCK_DAY_ALL")
    fun getStockDayAll(): Call<StockDayAll>

    @Headers("Content-type: application/json", "Accept: application/json")
    @GET("v1/exchangeReport/STOCK_DAY_AVG_ALL")
    fun getStockDayAvgAll(): Call<StockDayAvgAll>

    @Headers("Content-type: application/json", "Accept: application/json")
    @GET("v1/exchangeReport/BWIBBU_ALL")
    fun getBwibbuAll(): Call<BwibbuAll>

}