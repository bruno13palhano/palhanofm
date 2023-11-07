package com.bruno13palhano.core.network

import com.bruno13palhano.core.model.Sponsor
import com.bruno13palhano.core.network.PFDispatchers.IO
import com.bruno13palhano.core.network.model.asExternal
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class SponsorsNetworkImpl @Inject constructor(
    private val apiService: Service,
    @Dispatcher(IO) private val dispatcher: CoroutineDispatcher
) : SponsorsNetwork {
    override fun getAll(): Flow<List<Sponsor>> = flow {
        try {
            emit(apiService.getSponsors().map { it.asExternal() })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(dispatcher)
}