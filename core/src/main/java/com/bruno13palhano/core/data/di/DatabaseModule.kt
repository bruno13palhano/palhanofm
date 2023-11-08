package com.bruno13palhano.core.data.di

import android.content.Context
import cache.SchedulesTableQueries
import cache.SponsorsTableQueries
import com.bruno13palhano.cache.PalhanoFmDatabase
import com.bruno13palhano.core.data.database.DatabaseFactory
import com.bruno13palhano.core.data.database.DriverFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabaseFactoryDriver(
        @ApplicationContext context: Context
    ): PalhanoFmDatabase {
        return DatabaseFactory(
            driverFactory = DriverFactory(context = context)
        ).createDriver()
    }

    @Provides
    @Singleton
    fun providesSponsorsTable(
        database: PalhanoFmDatabase
    ): SponsorsTableQueries = database.sponsorsTableQueries

    @Provides
    @Singleton
    fun providesSchedulesTable(
        database: PalhanoFmDatabase
    ): SchedulesTableQueries = database.schedulesTableQueries
}