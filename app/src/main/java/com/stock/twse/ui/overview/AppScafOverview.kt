package com.stock.twse.ui.overview

import StockDayAvgAll
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stock.twse.R
import com.stock.twse.StockDayAll
import com.stock.twse.data.BwibbuAll
import com.stock.twse.ui.components.ClickableText
import com.stock.twse.ui.components.MinimalDialog
import com.stock.twse.ui.theme.TWSETheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    dataStockDayAll: StockDayAll = emptyList(),
    dataStockDayAvgAll: StockDayAvgAll = emptyList(),
    dataBwibbuAll: BwibbuAll = emptyList(),
    dataSelectedCardCode: String? = null,
    onClickCard: (String) -> Unit = {},
    onClickBottom: (Boolean) -> Unit = {}
) {
    var shouldShowDialog by remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    var showBottomSheet by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },

                actions = {
                    Row(modifier = Modifier.padding(end = 10.dp)) {
                        CompositionLocalProvider(
                            LocalRippleConfiguration provides null
                        ) {
                            IconButton(
                                onClick = {
                                    showBottomSheet = true
                                },
                                modifier = Modifier
                                    .border(
                                        width = 0.5.dp,
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        shape = RoundedCornerShape(5.dp),
                                    )
                                    .width(32.dp)
                                    .height(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Menu,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) {
        Surface(modifier = Modifier.padding(it)) {
            OverviewScreen(
                dataStockDayAll,
                dataStockDayAvgAll,
                {
                    onClickCard.invoke(it)
                    shouldShowDialog = true
                },
                modifier = Modifier.padding(8.dp)
            )
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = rememberModalBottomSheetState()
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()

                    ) {
                        ClickableText(text = "依股票代號降序", {
                            onClickBottom(true)
                            showBottomSheet = false
                        })
                        ClickableText(text = "依股票代號升序", {
                            onClickBottom(false)
                            showBottomSheet = false
                        })
                    }
                }
            }

            if (shouldShowDialog && dataSelectedCardCode != null && dataBwibbuAll.isNotEmpty()) {
                val dataBwibbuAllMap = dataBwibbuAll.associateBy { it.Code }
                MinimalDialog(
                    { shouldShowDialog = false },
                    dataBwibbuAllMap.get(dataSelectedCardCode)?.PEratio.toString(),
                    dataBwibbuAllMap.get(dataSelectedCardCode)?.DividendYield.toString(),
                    dataBwibbuAllMap.get(dataSelectedCardCode)?.PBratio.toString()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TWSETheme {
        AppScaffold()
    }
}