package bruhcollective.itaysonlab.psapp.core.models.auth

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SsoTokenRequest(
    val grant_type: String,
    val client_id: String,
    val client_secret: String,
    val scope: String
)