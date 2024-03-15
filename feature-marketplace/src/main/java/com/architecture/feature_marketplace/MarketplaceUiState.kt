package com.architecture.feature_marketplace

import com.architecture.core.model.Ticker
import com.architecture.core.state.BaseState
import com.architecture.core.state.UiState

data class MarketplaceUiState(
    val tickers: UiState<List<Ticker>> = UiState.Loading,
    val isOffline: Boolean = false,
    val query: String = "",
) : BaseState {
    val isTickersEmpty: Boolean = tickers is UiState.Success && tickers.data.isEmpty()
}
