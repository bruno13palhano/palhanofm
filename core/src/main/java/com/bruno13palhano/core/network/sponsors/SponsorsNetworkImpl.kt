package com.bruno13palhano.core.network.sponsors

import com.bruno13palhano.core.model.Sponsor
import com.bruno13palhano.core.network.Service
import com.bruno13palhano.core.network.model.asExternal
import java.lang.Exception
import javax.inject.Inject

internal class SponsorsNetworkImpl @Inject constructor(
    private val apiService: Service
) : SponsorsNetwork {
    override suspend fun getAll(): List<Sponsor> {
        return try {
            apiService.getSponsors().map { it.asExternal() }
        } catch (ignored: Exception) { emptyList() }
    }
}