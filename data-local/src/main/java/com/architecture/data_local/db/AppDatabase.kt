package com.architecture.data_local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.architecture.data_local.db.ticker.TickerDao
import com.architecture.data_local.db.ticker.TickerEntity

const val DATABASE_NAME = "crypto-db"

@Database(entities = [TickerEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tickerDao(): TickerDao

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }

}
