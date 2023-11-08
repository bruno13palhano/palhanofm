package com.bruno13palhano.core.network.schedules

import com.bruno13palhano.core.model.Schedule

internal interface SchedulesNetwork {
    suspend fun getAll(): List<Schedule>
}