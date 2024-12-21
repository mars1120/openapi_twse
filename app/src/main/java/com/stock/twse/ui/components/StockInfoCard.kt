package com.stock.twse.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun StockInfoCard(
    stockCode: String = "",
    stockName: String = "",
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray, //Card background color
            contentColor = Color.White  //Card content color,e.g.text
        ),
        modifier = modifier
            .fillMaxWidth(), onClick = {}
//            .size(width = 240.dp, height = 100.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // 股票代號和名稱
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "($stockCode)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "($stockName)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 價格資訊網格
            Column(modifier = Modifier.fillMaxWidth()) {
                PriceRow(
                    leftLabel = "開盤價",
                    leftValue = "(開盤價)",
                    rightLabel = "收盤價",
                    rightValue = "(收盤價)",
                    rightValueColor = Color.Green
                )
                PriceRow(
                    leftLabel = "最高價",
                    leftValue = "(最高價)",
                    rightLabel = "最低價",
                    rightValue = "(最低價)"
                )
                PriceRow(
                    leftLabel = "漲跌價差",
                    leftValue = "(漲跌價差)",
                    leftValueColor = Color.Green,
                    rightLabel = "月平均價",
                    rightValue = "(月平均價)"
                )

                Spacer(modifier = Modifier.height(8.dp))


                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                        Text(text = "成交筆數", color = MaterialTheme.colorScheme.onSurface)
                    }
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        Text(text = "成交股數", color = MaterialTheme.colorScheme.onSurface)
                    }
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
                        Text(text = "成交金額", color = MaterialTheme.colorScheme.onSurface)
                    }
                }
            }
        }
    }
}

@Composable
private fun PriceRow(
    leftLabel: String,
    leftValue: String,
    rightLabel: String,
    rightValue: String,
    leftValueColor: Color = MaterialTheme.colorScheme.onSurface,
    rightValueColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // 左側
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = leftLabel,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Text(
                text = leftValue,
                style = MaterialTheme.typography.bodyMedium,
                color = leftValueColor
            )
        }

        Spacer(modifier = Modifier.width(32.dp))

        // 右側
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = rightLabel,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Text(
                text = rightValue,
                style = MaterialTheme.typography.bodyMedium,
                color = rightValueColor
            )
        }
    }
}

@Preview
@Composable
fun StockInfoCardPreview() {
    MaterialTheme {
        Surface {
            StockInfoCard(
                stockCode = "2330",
                stockName = "台積電",
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}