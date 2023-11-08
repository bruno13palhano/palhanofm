package com.bruno13palhano.core.repository.di

import com.bruno13palhano.core.model.Schedule
import com.bruno13palhano.core.repository.sponsors.SponsorsData
import com.bruno13palhano.core.model.Sponsor
import com.bruno13palhano.core.repository.schedules.SchedulesData
import com.bruno13palhano.core.repository.schedules.SchedulesLight
import com.bruno13palhano.core.repository.sponsors.SponsorsLight
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
internal annotation class InternalSponsorsLight

@Qualifier
internal annotation class InternalSchedulesLight

@InstallIn(SingletonComponent::class)
@Module
internal abstract class InternalDataModule {

    @InternalSponsorsLight
    @Singleton
    @Binds
    abstract fun bindInternalSponsorsLight(data: SponsorsLight): SponsorsData<Sponsor>

    @InternalSchedulesLight
    @Singleton
    @Binds
    abstract fun bindInternalSchedulesLight(data: SchedulesLight): SchedulesData<Schedule>
}