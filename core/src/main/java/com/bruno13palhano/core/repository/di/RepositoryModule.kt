package com.bruno13palhano.core.repository.di

import com.bruno13palhano.core.SponsorsData
import com.bruno13palhano.core.model.Sponsor
import com.bruno13palhano.core.repository.SponsorsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class SponsorsRep

@InstallIn(SingletonComponent::class)
@Module
internal abstract class RepositoryModule {

    @SponsorsRep
    @Singleton
    @Binds
    abstract fun bindSponsorsRepository(repository: SponsorsRepository): SponsorsData<Sponsor>
}