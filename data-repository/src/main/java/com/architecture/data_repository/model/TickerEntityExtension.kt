package com.architecture.data_repository.model

import com.architecture.core.model.Ticker
import com.architecture.data_local.db.ticker.TickerEntity

fun TickerEntity.asDomainModel() = Ticker(
    symbol = symbol,
    dailyChangeRelative = dailyChangeRelative,
    lastPrice = lastPrice,
)
