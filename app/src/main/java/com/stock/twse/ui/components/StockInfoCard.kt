package com.stock.twse.ui.components

import StockDayAvgAllItem
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
import androidx.compose.ui.unit.sp
import com.stock.twse.StockDayAllItem

@Composable
fun StockInfoCard(
    dataA: StockDayAllItem,
    dataB: StockDayAvgAllItem,
    onClickStockDayAll: (String) -> Unit = {},
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
            .fillMaxWidth(), onClick = {
            onClickStockDayAll(dataB.Code)
        }
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
                    text = dataA.Code,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = dataA.Name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                var MonthlyAveragePriceDouble = dataB.MonthlyAveragePrice.toDoubleOrNull() ?: 0.0
                var ClosingPriceDouble = dataA.ClosingPrice.toDoubleOrNull() ?: 0.0
                var varDif = ClosingPriceDouble - MonthlyAveragePriceDouble
                val ClosingPriceColor = when {
                    varDif > 0 -> Color.Red
                    varDif < 0 -> Color.Green
                    else -> MaterialTheme.colorScheme.onSurface
                }
                var ChangeDouble = dataA.Change.toDoubleOrNull() ?: 0.0
                val ChangeColor = when {
                    ChangeDouble > 0 -> Color.Red
                    ChangeDouble < 0 -> Color.Green
                    else -> MaterialTheme.colorScheme.onSurface
                }
                MaterialTheme.colorScheme.onSurface
                PriceRow(
                    leftLabel = "開盤價",
                    leftValue = dataA.OpeningPrice,
                    rightLabel = "收盤價",
                    rightValue = dataA.ClosingPrice,
                    rightValueColor = ClosingPriceColor
                )
                PriceRow(
                    leftLabel = "最高價",
                    leftValue = dataA.HighestPrice,
                    rightLabel = "最低價",
                    rightValue = dataA.LowestPrice
                )
                PriceRow(
                    leftLabel = "漲跌價差",
                    leftValue = dataA.Change,
                    leftValueColor = ChangeColor,
                    rightLabel = "月平均價",
                    rightValue = dataB.MonthlyAveragePrice
                )

                Spacer(modifier = Modifier.height(8.dp))


                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                        Text(
                            text = "成交筆數:${dataA.Transaction}",
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                        Text(
                            text = "成交股數:${dataA.TradeVolume}",
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                        Text(
                            text = "成交金額:${dataA.TradeValue}",
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
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
                StockDayAllItem(
                    Change = "-1.8000",
                    ClosingPrice = "192.60",
                    Code = "0050",
                    HighestPrice = "193.75",
                    LowestPrice = "192.00",
                    Name = "元大台灣50",
                    OpeningPrice = "193.75",
                    TradeValue = "2374265742",
                    TradeVolume = "12318276",
                    Transaction = "31645"
                ),
                StockDayAvgAllItem(
                    ClosingPrice = "192.60",
                    Code = "0050",
                    MonthlyAveragePrice = "195.00",
                    Name = "元大台灣50"
                ),
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}