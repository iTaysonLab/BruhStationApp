package bruhcollective.itaysonlab.psapp.core.models.profile

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserPersonalDetail(
    val firstName: String,
    val lastName: String
)