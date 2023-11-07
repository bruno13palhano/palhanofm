package com.bruno13palhano.core.repository

import com.bruno13palhano.core.SponsorsData
import com.bruno13palhano.core.di.InternalSponsorsLight
import com.bruno13palhano.core.model.Sponsor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class SponsorsRepository @Inject constructor(
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