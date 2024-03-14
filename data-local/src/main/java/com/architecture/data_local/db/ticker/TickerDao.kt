package com.architecture.data_local.db.ticker

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

/** The Data Access Object for the TickerEntity class. */
@Dao
interface TickerDao {

    @Query("SELECT * FROM ticker WHERE symbol LIKE :query")
    fun tickerList(query: String): Flow<List<TickerEntity>>

    @Upsert
    suspend fun insertTickerList(tickerList: List<TickerEntity>)

}
