package bruhcollective.itaysonlab.psapp.core.models.auth

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SsoCookieResponse(
    val npsso: String?,
    val expires_in: Int?,
    //
    val error: String?,
    val error_description: String?,
    val error_code: Int?,
    //
    val ticket_uuid: String?,
    val challenge_method: String?,
    val authentication_type: String?
)