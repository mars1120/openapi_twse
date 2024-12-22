package com.stock.twse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stock.twse.homepage.HomepageViewModel
import com.stock.twse.ui.overview.OverviewScreen
import com.stock.twse.ui.theme.TWSETheme

class MainActivity : ComponentActivity() {
    private val homepageViewModel: HomepageViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homepageViewModel.fetchData()
        enableEdgeToEdge()
        setContent {
            TWSETheme {
                AppScaffold()
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold() {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

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
                        IconButton(
                            onClick = { /* do something */ },
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
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) {
        Surface(modifier = Modifier.padding(it)) {
            OverviewScreen(
                stockCode = "2330",
                stockName = "台積電",
                modifier = Modifier.padding(8.dp)
            )
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