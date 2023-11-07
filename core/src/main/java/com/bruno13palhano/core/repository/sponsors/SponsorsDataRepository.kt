package com.bruno13palhano.core.repository.sponsors

interface SponsorsDataRepository<T> : SponsorsData<T> {
    suspend fun synchronize()
}