package com.bruno13palhano.core.network

import com.bruno13palhano.core.model.Sponsor
import kotlinx.coroutines.flow.Flow

interface SponsorsNetwork {
    fun getAll(): Flow<List<Sponsor>>
}