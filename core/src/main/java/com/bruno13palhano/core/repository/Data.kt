package com.bruno13palhano.core.repository

import kotlinx.coroutines.flow.Flow

interface Data<T> {
    suspend fun insert(data: T): Long
    suspend fun update(data: T)
    fun getAll(): Flow<List<T>>
}