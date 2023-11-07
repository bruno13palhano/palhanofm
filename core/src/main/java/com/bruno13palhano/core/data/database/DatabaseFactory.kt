package com.bruno13palhano.core.data.database

import com.bruno13palhano.cache.PalhanoFmDatabase

internal class DatabaseFactory(private val driverFactory: DriverFactory) {
    fun createDriver(): PalhanoFmDatabase {
        return PalhanoFmDatabase(driver = driverFactory.createDriver())
    }
}