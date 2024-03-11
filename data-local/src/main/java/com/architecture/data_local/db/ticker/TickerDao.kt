package com.architecture.data_local.db.ticker

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/** The Data Access Object for the TickerEntity class. */
@Dao
interface TickerDao {

    @Query("SELECT * FROM ticker")
    fun tickerList(): Flow<List<TickerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTickerList(tickerList: List<TickerEntity>)

}
