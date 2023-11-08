package com.bruno13palhano.core.repository.schedules

interface SchedulesDataRepository<T> : SchedulesData<T> {
    suspend fun synchronize()
}