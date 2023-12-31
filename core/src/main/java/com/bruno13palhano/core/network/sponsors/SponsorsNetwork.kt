package com.bruno13palhano.core.network.sponsors

import com.bruno13palhano.core.model.Sponsor

internal interface SponsorsNetwork {
    suspend fun getAll(): List<Sponsor>
}