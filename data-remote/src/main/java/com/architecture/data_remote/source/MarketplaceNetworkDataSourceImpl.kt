package com.architecture.data_remote.source

import com.architecture.data_remote.api.ticker.MarketplaceService
import com.architecture.data_remote.api.ticker.TickerApiModel
import javax.inject.Inject

class MarketplaceNetworkDataSourceImpl @Inject constructor(private val marketplaceService: MarketplaceService) :
    MarketplaceNetworkDataSource {

    override suspend fun getTickerList(symbols: String): List<TickerApiModel> =
        marketplaceService.getTickerList(symbols).map { pair ->
            TickerApiModel(
                symbol = pair[0] as String,
                dailyChangeRelative = pair[6] as Double,
                lastPrice = pair[7] as Double,
            )
        }

}
