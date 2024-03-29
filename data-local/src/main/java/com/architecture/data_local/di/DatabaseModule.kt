package com.architecture.data_local.di

import android.content.Context
import com.architecture.data_local.db.AppDatabase
import com.architecture.data_local.db.ticker.TickerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideTickerDao(appDatabase: AppDatabase): TickerDao = appDatabase.tickerDao()

}
