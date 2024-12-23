package com.stock.twse.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stock.twse.ui.theme.TWSETheme

@Composable
fun ClickableText(text: String, onClick: () -> Unit) {
    Box(modifier = Modifier
        .clickable() {
            onClick()
        }
        .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            text = text
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ClickableTextPreview() {
    TWSETheme {
        ClickableText("hello", {})
    }
}