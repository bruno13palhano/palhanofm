package com.bruno13palhano.core.model

data class Schedule(
    val id: Long,
    val title: String,
    val broadcaster: String,
    val startTime: String,
    val endTime: String
)
