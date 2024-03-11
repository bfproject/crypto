package com.architecture.data_repository.di

import com.architecture.core.repository.MarketplaceRepository
import com.architecture.data_repository.MarketplaceRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMarketplaceRepository(marketplaceRepositoryImpl: MarketplaceRepositoryImpl): MarketplaceRepository

}
