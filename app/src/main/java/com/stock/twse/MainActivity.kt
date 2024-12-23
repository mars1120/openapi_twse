package com.stock.twse

import StockDayAvgAll
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.stock.twse.data.BwibbuAll
import com.stock.twse.homepage.HomepageViewModel
import com.stock.twse.ui.components.ClickableText
import com.stock.twse.ui.components.MinimalDialog
import com.stock.twse.ui.overview.AppScaffold
import com.stock.twse.ui.overview.OverviewScreen
import com.stock.twse.ui.theme.TWSETheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val homepageViewModel: HomepageViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homepageViewModel.fetchData()
//        lifecycleScope.launch {
//            homepageViewModel.setClickedCode1(App.db.stockDayAllItemDao().getAll())
//
//        }
         enableEdgeToEdge()
        setContent {
            TWSETheme {
                initData(homepageViewModel)
            }
        }
    }
}

@Composable
private fun initData(viewModel: HomepageViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val dataStockDayAll = remember(uiState.stockDayAll) {
        uiState.stockDayAll ?: emptyList()
    }
    val dataStockDayAvgAll = remember(uiState.stockDayAvgAll) {
        uiState.stockDayAvgAll ?: emptyList()
    }
    val dataBwibbuAll = remember(uiState.bwibbuAll) {
        uiState.bwibbuAll ?: emptyList()
    }

    val selectedCardCode = remember(uiState.selectedCardCode) {
        uiState.selectedCardCode
    }

    AppScaffold(dataStockDayAll, dataStockDayAvgAll, dataBwibbuAll, selectedCardCode,
        { viewModel.setClickedCode(it) }, { viewModel.setSortByAsc(it) })
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TWSETheme {
        AppScaffold()
    }
}