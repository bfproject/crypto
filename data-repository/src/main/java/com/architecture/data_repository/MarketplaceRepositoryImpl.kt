package com.architecture.data_repository

import com.architecture.core.di.DefaultDispatcher
import com.architecture.core.di.IODispatcher
import com.architecture.core.model.Ticker
import com.architecture.core.repository.MarketplaceRepository
import com.architecture.core.state.DataState
import com.architecture.data_local.db.ticker.TickerDao
import com.architecture.data_local.db.ticker.TickerEntity
import com.architecture.data_remote.source.MarketplaceNetworkDataSource
import com.architecture.data_repository.model.asDomainModel
import com.architecture.data_repository.model.asEntity
import java.net.UnknownHostException
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class MarketplaceRepositoryImpl @Inject constructor(
    private val tickerDao: TickerDao,
    private val marketplaceNetworkDataSource: MarketplaceNetworkDataSource,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) : MarketplaceRepository {

    override suspend fun loadTickerList(): DataState<Unit> = withContext(ioDispatcher) {
        try {
            val tickerList = marketplaceNetworkDataSource.getTickerList(USD_SYMBOLS)
            tickerDao.insertTickerList(tickerList.map { it.asEntity() })
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

    override fun tickerList(): Flow<List<Ticker>> {
        return tickerDao.tickerList()
            .map { it.map(TickerEntity::asDomainModel) }
            .flowOn(defaultDispatcher)
    }

    companion object {
        private const val USD_SYMBOLS =
            "tBORG:USD,tBTCUSD,tETHUSD,tSOLUSD,tLTCUSD,tXRPUSD,tDSHUSD,tRRTUSD,tEOSUSD,tDOGE:USD," +
                    "tMATIC:USD,tNEXO:USD,tOCEAN:USD,tLUNA2:USD,tNOMUSD,tBEST:USD,tAAVE:USD,tPLUUSD,tFILUSD"
    }
}
