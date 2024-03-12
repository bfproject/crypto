package com.architecture.core.model

data class Ticker(
    val symbol: String,
    val dailyChangeRelative: Double,
    val lastPrice: Double,
)

fun Ticker.cryptoNamePair(): Pair<String, String> {
    val cryptoName = if (symbol.contains(":")) {
        symbol.substring(1, symbol.length - 4)
    } else {
        symbol.substring(1, symbol.length - 3)
    }
    val secondPart = symbol.substring(symbol.length - 3)
    return Pair(cryptoName, secondPart)
}
