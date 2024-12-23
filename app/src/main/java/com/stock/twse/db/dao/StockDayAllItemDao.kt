package com.stock.twse.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.stock.twse.StockDayAll


@Dao
interface StockDayAllItemDao {

    @Query("SELECT * FROM stockdayallitem")
    suspend  fun getAll(): StockDayAll

    @Insert
    fun insertAll(stockDayAvgAllItem: StockDayAll)

    @Query("DELETE FROM stockdayallitem")
    fun deleteAll()
}