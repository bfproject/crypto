package com.architecture.data_repository

import com.architecture.core.di.IODispatcher
import com.architecture.core.repository.MarketplaceRepository
import com.architecture.core.state.DataState
import com.architecture.data_remote.source.MarketplaceNetworkDataSource
import java.net.UnknownHostException
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class MarketplaceRepositoryImpl @Inject constructor(
    private val marketplaceNetworkDataSource: MarketplaceNetworkDataSource,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
) : MarketplaceRepository {

    override suspend fun loadTickerList(): DataState<Unit> = withContext(ioDispatcher) {
        try {
            val tickerList = marketplaceNetworkDataSource.getTickerList(USD_SYMBOLS)
            DataState.Success(Unit)
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException -> {
                    DataState.Error.NoConnection("")
                }

                else -> {
                    DataState.Error.Generic(e.message)
                }
            }
        }
    }

    companion object {
        private const val USD_SYMBOLS =
            "tBORG:USD,tBTCUSD,tETHUSD,tSOLUSD,tLTCUSD,tXRPUSD,tDSHUSD,tRRTUSD,tEOSUSD,tDOGE:USD," +
                    "tMATIC:USD,tNEXO:USD,tOCEAN:USD,tLUNA2:USD,tNOMUSD,tBEST:USD,tAAVE:USD,tPLUUSD,tFILUSD"
    }
}
