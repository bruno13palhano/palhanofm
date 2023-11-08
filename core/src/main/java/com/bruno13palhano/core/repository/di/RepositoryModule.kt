package com.bruno13palhano.core.repository.di

import com.bruno13palhano.core.model.Schedule
import com.bruno13palhano.core.model.Sponsor
import com.bruno13palhano.core.repository.schedules.SchedulesDataRepository
import com.bruno13palhano.core.repository.schedules.SchedulesRepository
import com.bruno13palhano.core.repository.sponsors.SponsorsDataRepository
import com.bruno13palhano.core.repository.sponsors.SponsorsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class SponsorsRep

@Qualifier
annotation class SchedulesRep

@InstallIn(SingletonComponent::class)
@Module
internal abstract class RepositoryModule {

    @SponsorsRep
    @Singleton
    @Binds
    abstract fun bindSponsorsRepository(
        repository: SponsorsRepository
    ): SponsorsDataRepository<Sponsor>

    @SchedulesRep
    @Singleton
    @Binds
    abstract fun bindSchedulesRepository(
        repository: SchedulesRepository
    ): SchedulesDataRepository<Schedule>
}