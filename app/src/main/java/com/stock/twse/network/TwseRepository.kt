package com.stock.twse.network


import StockDayAvgAll
import com.stock.twse.App
import com.stock.twse.StockDayAll
import com.stock.twse.data.BwibbuAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

//open api url: https://openapi.twse.com.tw/
interface ITravelRepository {
    fun getStockDayAll(): Flow<Result<StockDayAll>>
    fun getStockDayAvgAll(): Flow<Result<StockDayAvgAll>>
    fun getBwibbuAll(): Flow<Result<BwibbuAll>>

}

class TwseRepositoryImpl : ITravelRepository {


    override fun getStockDayAll(): Flow<Result<StockDayAll>> = flow {
        val stockDayAll = NetworkRequest.getStockDayAll()
        App.db.stockDayAllItemDao().deleteAll()
        App.db.stockDayAllItemDao().insertAll(stockDayAll)
        emit(Result.success(stockDayAll))
    }.flowOn(Dispatchers.IO)
        .catch { e ->
            emit(Result.failure(e))
        }
    override fun getStockDayAvgAll(): Flow<Result<StockDayAvgAll>> = flow {
        val stockDayAvgAll = NetworkRequest.getStockDayAvgAll()
        emit(Result.success(stockDayAvgAll))
    }.flowOn(Dispatchers.IO)
        .catch { e ->
            emit(Result.failure(e))
        }
    override fun getBwibbuAll(): Flow<Result<BwibbuAll>> = flow {
        val BwibbuAll = NetworkRequest.getBwibbuAll()
        emit(Result.success(BwibbuAll))
    }.flowOn(Dispatchers.IO)
        .catch { e ->
            emit(Result.failure(e))
        }



}