package com.bruno13palhano.core.network.schedules

import com.bruno13palhano.core.model.Schedule
import com.bruno13palhano.core.network.Service
import com.bruno13palhano.core.network.model.asExternal
import javax.inject.Inject

internal class SchedulesNetworkImpl @Inject constructor(
    private val apiService: Service
) : SchedulesNetwork {
    override suspend fun getAll(): List<Schedule> {
        return try {
            apiService.getSchedules().map { it.asExternal() }
        } catch (ignored: Exception) { emptyList() }
    }
}