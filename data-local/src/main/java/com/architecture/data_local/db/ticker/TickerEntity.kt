package com.architecture.data_local.db.ticker

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "ticker",
    indices = [Index(value = ["symbol"], unique = true)]
)
data class TickerEntity(
    @PrimaryKey @ColumnInfo(name = "symbol") val symbol: String,
    @ColumnInfo(name = "daily_change_relative") val dailyChangeRelative: Double,
    @ColumnInfo(name = "last_price") val lastPrice: Double
)
