package com.architecture.data_remote.api.ticker

data class TickerApiModel(
    val symbol: String,
    val dailyChangeRelative: Double,
    val lastPrice: Double,
)
