package com.bruno13palhano.core.repository.schedules

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import cache.SchedulesTableQueries
import com.bruno13palhano.core.di.Dispatcher
import com.bruno13palhano.core.di.PFDispatchers.IO
import com.bruno13palhano.core.model.Schedule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class SchedulesLight @Inject constructor(
    private val schedulesQueries: SchedulesTableQueries,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : SchedulesData<Schedule> {
    override suspend fun insert(data: Schedule): Long {
        schedulesQueries.insert(
            id = data.id,
            title = data.title,
            broadcaster = data.broadcaster,
            startTime = data.startTime,
            endTime = data.endTime
        )

        return schedulesQueries.getLastId().executeAsOne()
    }

    override suspend fun update(data: Schedule) {
        schedulesQueries.update(
            title = data.title,
            broadcaster = data.broadcaster,
            startTime = data.startTime,
            endTime = data.endTime,
            id = data.id
        )
    }

    override fun getAll(): Flow<List<Schedule>> =
        schedulesQueries.getAll(::mapSchedules).asFlow().mapToList(ioDispatcher)

    private fun mapSchedules(
        id: Long,
        title: String,
        broadcaster: String,
        startTime: String,
        endTime:String
    ) = Schedule(
        id = id,
        title = title,
        broadcaster = broadcaster,
        startTime = startTime,
        endTime = endTime
    )
}