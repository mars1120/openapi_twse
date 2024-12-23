package com.stock.twse.ui.overview

import StockDayAvgAll
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.stock.twse.StockDayAll
import com.stock.twse.StockDayAllItem
import com.stock.twse.ui.components.StockInfoCard
import com.stock.twse.ui.theme.TWSETheme

@Composable
fun OverviewScreen(
    dataA: StockDayAll? = null,
    dataB: StockDayAvgAll? = null,
    onClickStockDayAll: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = rememberLazyListState(),
        modifier = Modifier.padding(8.dp)
    ) {

        if (dataA != null && dataB != null) {
            val dataASet = dataA.associateBy { it.Code }
            items(dataB.size) { index ->
                StockInfoCard(
                    dataASet.get(dataB[index].Code)!!,
                    dataB[index],
                    onClickStockDayAll,
                    modifier
                )
            }
        }
    }

}


// 使用範例
@Preview
@Composable
fun StockInfoCardPreview() {

    val mockStockDayAllResult = "[" +
            "{\"Code\":\"0050\",\"Name\":\"元大台灣50\",\"TradeVolume\":\"12318276\",\"TradeValue\":\"2374265742\",\"OpeningPrice\":\"193.75\",\"HighestPrice\":\"193.75\",\"LowestPrice\":\"192.00\",\"ClosingPrice\":\"192.60\",\"Change\":\"-1.8000\",\"Transaction\":\"31645\"},\n" +
            "{\"Code\":\"0051\",\"Name\":\"元大中型100\",\"TradeVolume\":\"110412\",\"TradeValue\":\"8436684\",\"OpeningPrice\":\"76.70\",\"HighestPrice\":\"76.70\",\"LowestPrice\":\"76.15\",\"ClosingPrice\":\"76.20\",\"Change\":\"-0.5000\",\"Transaction\":\"369\"},\n" +
            "{\"Code\":\"0052\",\"Name\":\"富邦科技\",\"TradeVolume\":\"588596\",\"TradeValue\":\"111997291\",\"OpeningPrice\":\"191.50\",\"HighestPrice\":\"191.50\",\"LowestPrice\":\"189.60\",\"ClosingPrice\":\"190.25\",\"Change\":\"-2.6500\",\"Transaction\":\"1847\"}]";
    val mockStockDayAll = Gson().fromJson<List<StockDayAllItem>>(
        mockStockDayAllResult,
        object : TypeToken<List<StockDayAllItem>>() {}.type
    )

    val mockStockAvgDayAllResult = "[" +
            "{\"Code\":\"0050\",\"Name\":\"元大台灣50\",\"ClosingPrice\":\"192.60\",\"MonthlyAveragePrice\":\"195.00\"},\n" +
            "{\"Code\":\"0051\",\"Name\":\"元大中型100\",\"ClosingPrice\":\"76.20\",\"MonthlyAveragePrice\":\"77.72\"},\n" +
            "{\"Code\":\"0052\",\"Name\":\"富邦科技\",\"ClosingPrice\":\"190.25\",\"MonthlyAveragePrice\":\"191.99\"}]"
    val mockStockAvgDayAll = Gson().fromJson<StockDayAvgAll>(
        mockStockAvgDayAllResult,
        object : TypeToken<StockDayAvgAll>() {}.type
    )
    TWSETheme {
        Surface {
            OverviewScreen(
                mockStockDayAll,
                mockStockAvgDayAll,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
