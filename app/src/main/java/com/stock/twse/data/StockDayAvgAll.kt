

typealias StockDayAvgAll = List<StockDayAvgAllItem>

data class StockDayAvgAllItem(
    val ClosingPrice: String,
    val Code: String,
    val MonthlyAveragePrice: String,
    val Name: String
)