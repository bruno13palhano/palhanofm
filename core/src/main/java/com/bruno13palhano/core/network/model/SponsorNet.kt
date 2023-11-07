package com.bruno13palhano.core.network.model

import com.bruno13palhano.core.model.Sponsor
import com.squareup.moshi.Json

internal data class SponsorNet(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "imageUrl") val imageUrl: String,
    @Json(name = "url") val url: String,
    @Json(name = "contact") val contact: String
)

internal fun SponsorNet.asExternal() = Sponsor(
    id = id,
    name = name,
    imageUrl = imageUrl,
    url = url,
    contact = contact
)

internal fun Sponsor.asInternal() = SponsorNet(
    id = id,
    name = name,
    imageUrl = imageUrl,
    url = url,
    contact = contact
)