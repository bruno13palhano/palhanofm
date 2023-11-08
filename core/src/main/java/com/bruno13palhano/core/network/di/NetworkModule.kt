package com.bruno13palhano.core.network.di

import com.bruno13palhano.core.network.sponsors.SponsorsNetwork
import com.bruno13palhano.core.network.sponsors.SponsorsNetworkImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
internal annotation class SponsorsNet

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NetworkModule {

    @SponsorsNet
    @Singleton
    @Binds
    abstract fun bindSponsorsNetwork(sponsorsNetwork: SponsorsNetworkImpl): SponsorsNetwork
}