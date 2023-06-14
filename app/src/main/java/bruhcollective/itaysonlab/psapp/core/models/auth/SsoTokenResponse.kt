package bruhcollective.itaysonlab.psapp.core.models.auth

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SsoTokenResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: Long,
    val id_token: String,
    val refresh_token: String,
    val refresh_token_expires_in: Long
)