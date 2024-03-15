package com.architecture.core.state

import com.architecture.core.model.Ticker

data class MarketplaceUiState(
    val tickers: UiState<List<Ticker>> = UiState.Loading,
    val isOffline: Boolean = false,
    val query: String = "",
) : BaseState {
    val isTickersEmpty: Boolean = tickers is UiState.Success && tickers.data.isEmpty()
    val contentTransparency: Float = if (isOffline) 0.6f else 1f
}
