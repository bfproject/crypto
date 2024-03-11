package com.architecture.data_remote.di

import com.architecture.data_remote.source.MarketplaceNetworkDataSource
import com.architecture.data_remote.source.MarketplaceNetworkDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkDataSourceModule {

    @Binds
    abstract fun bindMarketplaceNetworkDataSource(
        marketplaceNetworkDataSourceImpl: MarketplaceNetworkDataSourceImpl
    ): MarketplaceNetworkDataSource

}
