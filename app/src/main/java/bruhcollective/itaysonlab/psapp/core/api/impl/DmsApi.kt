package bruhcollective.itaysonlab.psapp.core.api.impl

import bruhcollective.itaysonlab.psapp.core.models.dms.DmsResponse
import retrofit2.http.GET

interface DmsApi {
    @GET("api/v1/devices/accounts/me")
    suspend fun getMeAndMyDevices(): DmsResponse
}