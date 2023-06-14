package bruhcollective.itaysonlab.psapp.core.api.impl

import bruhcollective.itaysonlab.psapp.core.models.dms.DmsResponse
import bruhcollective.itaysonlab.psapp.core.models.rdl.RdlEntitlement
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RdlApi {
    /*
    {
  "downloadStatuses": [
    {
      "entitlementId": "CUSA_CODE",
      "platform": "ps4",
      "status": "notstarted" / "playable"
    },
  ],
  "totalItemCount": 7
}
     */

    @POST("api/rdl/v2/status")
    suspend fun addItemToRdlQueue(
        @Body entitlements: List<RdlEntitlement>
    ): DmsResponse

    @GET("api/rdl/v2/status")
    suspend fun getRdlStatus(
        @Query("platform") platform: String,
        @Query("entitlementIds") entitlementIds: String,
        @Query("status_types") statusTypes: String, // started,notstarted,stopped,paused,waitfordownload,completed,usercancelled,playable,ps4cancelled
    ): DmsResponse
}