package com.architecture.core.repository

import com.architecture.core.model.Ticker
import com.architecture.core.state.DataState
import kotlinx.coroutines.flow.Flow

interface MarketplaceRepository {
    suspend fun loadTickerList(): DataState<Unit>
    fun tickerList(): Flow<List<Ticker>>
}
