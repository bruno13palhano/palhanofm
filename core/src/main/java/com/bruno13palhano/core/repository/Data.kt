package com.bruno13palhano.core

import kotlinx.coroutines.flow.Flow

interface Data<T> {
    suspend fun insert(data: T): Long
    suspend fun update(data: T)
    fun getAll(): Flow<List<T>>
}