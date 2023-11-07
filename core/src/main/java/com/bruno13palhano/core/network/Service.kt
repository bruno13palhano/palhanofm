package com.bruno13palhano.core.network

import com.bruno13palhano.core.network.model.SponsorNet
import retrofit2.http.GET

internal interface Service {

    @GET("sponsors/all")
    suspend fun getSponsors(): List<SponsorNet>
}