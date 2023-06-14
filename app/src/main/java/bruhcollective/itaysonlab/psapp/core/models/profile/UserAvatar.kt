package bruhcollective.itaysonlab.psapp.core.models.profile

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserAvatar(
    val url: String,
    val size: String
)