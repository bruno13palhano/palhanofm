package com.bruno13palhano.core.di

import com.bruno13palhano.core.SponsorsData
import com.bruno13palhano.core.model.Sponsor
import com.bruno13palhano.core.repository.SponsorsLight
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
internal annotation class InternalSponsorsLight

@InstallIn(SingletonComponent::class)
@Module
internal abstract class InternalDataModule {

    @InternalSponsorsLight
    @Singleton
    @Binds
    abstract fun bindInternalSponsorsLight(data: SponsorsLight): SponsorsData<Sponsor>
}