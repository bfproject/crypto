package com.architecture.data_repository.model

import com.architecture.data_local.db.ticker.TickerEntity
import com.architecture.data_remote.api.ticker.TickerApiModel

fun TickerApiModel.asEntity() = TickerEntity(
    symbol = symbol,
    dailyChangeRelative = dailyChangeRelative,
    lastPrice = lastPrice,
)
