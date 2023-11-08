package com.bruno13palhano.core.network.model

import com.bruno13palhano.core.model.Schedule
import com.squareup.moshi.Json

internal data class ScheduleNet(
    @Json(name = "id") val id: Long,
    @Json(name = "title") val title: String,
    @Json(name = "broadcaster") val broadcaster: String,
    @Json(name = "startTime") val startTime: String,
    @Json(name = "endTime") val endTime: String
)

internal fun ScheduleNet.asExternal() = Schedule(
    id = id,
    title = title,
    broadcaster = broadcaster,
    startTime = startTime,
    endTime = endTime
)