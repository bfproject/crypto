package com.architecture.core.repository

import com.architecture.core.state.DataState

interface MarketplaceRepository {
    suspend fun loadTickerList(): DataState<Unit>
}
