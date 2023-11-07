package com.bruno13palhano.core.repository

import com.bruno13palhano.core.SponsorsData
import com.bruno13palhano.core.repository.di.InternalSponsorsLight
import com.bruno13palhano.core.model.Sponsor
import com.bruno13palhano.core.network.SponsorsNetwork
import com.bruno13palhano.core.network.di.SponsorsNet
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class SponsorsRepository @Inject constructor(
    @SponsorsNet private val sponsorsNet: SponsorsNetwork,
    @InternalSponsorsLight private val sponsorsData: SponsorsData<Sponsor>
) : SponsorsData<Sponsor> {
    override suspend fun insert(data: Sponsor): Long {
        return sponsorsData.insert(data = data)
    }

    override suspend fun update(data: Sponsor) {
        sponsorsData.update(data = data)
    }

    override fun getAll(): Flow<List<Sponsor>> {
        return sponsorsData.getAll()
    }

}