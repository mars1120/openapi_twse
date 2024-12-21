package com.stock.twse.ui.overview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stock.twse.ui.components.StockInfoCard
import com.stock.twse.ui.theme.TWSETheme

@Composable
fun OverviewScreen(
    stockCode: String = "",
    stockName: String = "",
    modifier: Modifier = Modifier
) {
    StockInfoCard(stockCode, stockName, modifier)
}


// 使用範例
@Preview
@Composable
fun StockInfoCardPreview() {
    TWSETheme {
        Surface {
            OverviewScreen(
                stockCode = "2330",
                stockName = "台積電",
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
