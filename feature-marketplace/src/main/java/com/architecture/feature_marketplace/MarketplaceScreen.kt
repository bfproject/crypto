package com.architecture.feature_marketplace

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.architecture.core.model.Ticker
import com.architecture.core.model.cryptoNamePair
import com.architecture.core.state.UiState
import com.architecture.feature_marketplace.common.CustomCircularProgressIndicator
import com.architecture.feature_marketplace.common.CustomEmptyOrErrorState
import com.architecture.feature_marketplace.common.SearchBox
import com.architecture.feature_marketplace.common.formatPercent
import com.architecture.feature_marketplace.common.percentColor
import kotlinx.coroutines.delay

@Composable
fun MarketplaceScreen(viewModel: MarketplaceViewModel = viewModel()) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    // Collect the latest UI state
    val state = viewModel.uiStateFlow.collectAsStateWithLifecycle().value

    LaunchedEffect(true) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            // Refresh data every 5 seconds
            while (true) {
                viewModel.pollingData()
                delay(5000)
            }
        }
    }

    MarketplaceMainView(
        state = state,
        onValueChange = {
            viewModel.searchTicker(it)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketplaceMainView(
    state: MarketplaceUiState,
    onValueChange: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = stringResource(id = R.string.marketplace_screen_title).uppercase(),
                    fontWeight = FontWeight.Bold
                )
            })
        },
        content = {
            Column(
                modifier = Modifier.padding(it),
            ) {
                SearchBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .height(52.dp),
                    query = state.query,
                    onValueChange = onValueChange,
                )
                if (state.isOffline) {
                    OfflineMessage()
                }
                MarketplaceContent(state)
            }
        }
    )
}

@Composable
fun OfflineMessage() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .padding(end = 4.dp)
                .size(24.dp),
            painter = painterResource(R.drawable.ic_offline),
            contentDescription = null
        )
        Text(
            text = stringResource(R.string.error_msg_no_connection),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
        )
    }
}

@Composable
fun MarketplaceContent(state: MarketplaceUiState) {
    if (state.isTickersEmpty) {
        CustomEmptyOrErrorState(
            modifier = Modifier.offset(y = (80.dp)),
            drawableResId = R.drawable.ic_search,
            textResId = R.string.search_no_match
        )
    } else {
        when (state.tickers) {
            is UiState.Success -> {
                LazyColumn(modifier = Modifier.alpha(if (state.isOffline) 0.6f else 1f)) {
                    items(state.tickers.data) { item ->
                        TickerListItem(item)
                    }
                }
            }

            is UiState.Loading -> CustomCircularProgressIndicator(modifier = Modifier.fillMaxHeight())
            is UiState.Error -> CustomEmptyOrErrorState(
                drawableResId = R.drawable.ic_error,
                textResId = R.string.error_msg_retrieving_ticker_list
            )
        }
    }
}

@Composable
fun TickerListItem(ticker: Ticker) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row {
                Text(
                    text = ticker.cryptoNamePair().first,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "/${ticker.cryptoNamePair().second}",
                    modifier = Modifier
                        .padding(end = 8.dp),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "$${ticker.lastPrice}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    text = ticker.dailyChangeRelative.formatPercent(),
                    style = MaterialTheme.typography.labelMedium,
                    color = ticker.dailyChangeRelative.percentColor,
                )
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun RowTickerListItemPreview() {
    TickerListItem(ticker = Ticker("tBTCUSD", 0.5, 65000.0))
}


@Preview(showBackground = false)
@Composable
fun HeaderPreview() {
    OfflineMessage()
}
