package com.architecture.data_remote.api.ticker

import retrofit2.http.GET
import retrofit2.http.Query

interface MarketplaceService {
    @GET("tickers")
    suspend fun getTickerList(
        @Query("symbols") symbols: String,
    ): List<List<Any>>
}
