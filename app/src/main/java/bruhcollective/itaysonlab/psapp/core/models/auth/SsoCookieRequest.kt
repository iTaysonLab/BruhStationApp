package bruhcollective.itaysonlab.psapp.core.models.auth

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SsoCookieRequest(
    val authentication_type: String,
    val client_id: String,
    val username: String,
    val password: String,
    val ticket_uuid: String?,
    val code: String?
)