package com.architecture.core.model

data class Ticker(
    val symbol: String,
    val dailyChangeRelative: Double,
    val lastPrice: Double,
)
