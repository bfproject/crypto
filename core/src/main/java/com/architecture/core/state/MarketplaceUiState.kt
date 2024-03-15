package com.architecture.core.state

import com.architecture.core.model.Ticker

data class MarketplaceUiState(
    val tickers: UiState<List<Ticker>> = UiState.Loading,
) : BaseState {
    val isTickersEmpty: Boolean = tickers is UiState.Success && tickers.data.isEmpty()
}
