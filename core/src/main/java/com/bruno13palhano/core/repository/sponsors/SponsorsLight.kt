package com.bruno13palhano.core.repository.sponsors

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import cache.SponsorsTableQueries
import com.bruno13palhano.core.model.Sponsor
import com.bruno13palhano.core.di.Dispatcher
import com.bruno13palhano.core.di.PFDispatchers.IO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class SponsorsLight @Inject constructor(
    private val sponsorsQueries: SponsorsTableQueries,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : SponsorsData<Sponsor> {
    override suspend fun insert(data: Sponsor): Long {
        sponsorsQueries.insert(
            id = data.id,
            name = data.name,
            imageUrl = data.imageUrl,
            url = data.url,
            contact = data.contact
        )

        return sponsorsQueries.getLastId().executeAsOne()
    }

    override suspend fun update(data: Sponsor) {
        sponsorsQueries.update(
            name = data.name,
            imageUrl = data.imageUrl,
            url = data.url,
            contact = data.contact,
            id = data.id
        )
    }

    override fun getAll(): Flow<List<Sponsor>> =
        sponsorsQueries.getAll(::mapSponsor).asFlow().mapToList(ioDispatcher)

    private fun mapSponsor(
        id: Long,
        name: String,
        imageUrl: String,
        url: String,
        contact: String
    ) = Sponsor(
        id = id,
        name = name,
        imageUrl = imageUrl,
        url = url,
        contact = contact
    )
}