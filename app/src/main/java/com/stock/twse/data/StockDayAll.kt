package com.stock.twse

import androidx.room.Entity
import androidx.room.PrimaryKey

typealias StockDayAll = List<StockDayAllItem>

@Entity
data class StockDayAllItem(
    val Change: String,
    val ClosingPrice: String,
    @PrimaryKey val Code: String,
    val HighestPrice: String,
    val LowestPrice: String,
    val Name: String,
    val OpeningPrice: String,
    val TradeValue: String,
    val TradeVolume: String,
    val Transaction: String
)