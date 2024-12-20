package com.stock.twse.network


import com.stock.twse.data.BwibbuAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

//open api url: https://openapi.twse.com.tw/
interface ITravelRepository {
    fun getBwibbuAll(): Flow<Result<BwibbuAll>>
}

class TwseRepositoryImpl : ITravelRepository {

    override fun getBwibbuAll(): Flow<Result<BwibbuAll>> = flow {
        val attractionsAll = NetworkRequest.getBwibbuAll()
        emit(Result.success(attractionsAll))
    }.flowOn(Dispatchers.IO)
        .catch { e ->
            emit(Result.failure(e))
        }
}