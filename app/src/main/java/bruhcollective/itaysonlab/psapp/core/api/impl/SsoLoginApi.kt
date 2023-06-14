package bruhcollective.itaysonlab.psapp.core.api.impl

import bruhcollective.itaysonlab.psapp.core.models.auth.SsoTokenResponse
import retrofit2.http.*

interface SsoLoginApi {
    @FormUrlEncoded
    @POST("api/authz/v3/oauth/token")
    suspend fun ssoToken(@Header("Cookie") ssoCookie: String, @Header("Authorization") auth: String, @FieldMap query: Map<String, String>): SsoTokenResponse
}