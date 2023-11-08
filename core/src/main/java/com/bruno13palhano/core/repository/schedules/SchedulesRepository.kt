package com.bruno13palhano.core.repository.schedules

import com.bruno13palhano.core.model.Schedule
import com.bruno13palhano.core.network.di.SchedulesNet
import com.bruno13palhano.core.network.schedules.SchedulesNetwork
import com.bruno13palhano.core.repository.di.InternalSchedulesLight
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class SchedulesRepository @Inject constructor(
    @SchedulesNet private val schedulesNet: SchedulesNetwork,
    @InternalSchedulesLight private val schedulesData: SchedulesData<Schedule>
) : SchedulesDataRepository<Schedule> {
    override suspend fun insert(data: Schedule): Long {
        return schedulesData.insert(data = data)
    }

    override suspend fun update(data: Schedule) {
        schedulesData.update(data = data)
    }

    override fun getAll(): Flow<List<Schedule>> {
        return schedulesData.getAll()
    }

    override suspend fun synchronize() {
        val schedules = schedulesNet.getAll()
        schedules.forEach { schedulesData.insert(it) }
    }
}