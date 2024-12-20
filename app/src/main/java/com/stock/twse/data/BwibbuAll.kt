package com.stock.twse.data


typealias BwibbuAll = List<BwibbuInfo>

data class BwibbuInfo(
    val Code: String,
    val Name: String,
    val PEratio: String,
    val DividendYield: String,
    val PBratio: String
)
