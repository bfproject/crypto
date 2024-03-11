package com.architecture.data_remote.source

import com.architecture.data_remote.api.ticker.TickerApiModel

interface MarketplaceNetworkDataSource {
    suspend fun getTickerList(symbols: String): List<TickerApiModel>
}
