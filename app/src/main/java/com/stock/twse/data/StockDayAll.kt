package com.stock.twse

typealias StockDayAll = List<StockDayAllItem>

data class StockDayAllItem(
    val Change: String,
    val ClosingPrice: String,
    val Code: String,
    val HighestPrice: String,
    val LowestPrice: String,
    val Name: String,
    val OpeningPrice: String,
    val TradeValue: String,
    val TradeVolume: String,
    val Transaction: String
)