package com.bruno13palhano.core.network

import com.bruno13palhano.core.model.Sponsor
import kotlinx.coroutines.flow.Flow

internal interface SponsorsNetwork {
    suspend fun getAll(): List<Sponsor>
}