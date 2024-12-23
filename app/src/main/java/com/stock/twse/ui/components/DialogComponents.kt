package com.stock.twse.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.stock.twse.ui.theme.TWSETheme

@Composable
fun MinimalDialog(
    onDismissRequest: () -> Unit,
    PEratio: String = "",
    DividendYield: String = "",
    MonthlyAveragePrice: String = ""
) {

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                Text(
                    text = "本益比:${PEratio}",
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = "殖利率:${DividendYield}%",
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = "股價淨值比:${MonthlyAveragePrice}",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MinimalDialogPreview() {
    TWSETheme {
        MinimalDialog( {})
    }
}