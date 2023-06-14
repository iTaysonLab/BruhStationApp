package bruhcollective.itaysonlab.psapp.core.models.entitlement

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GameEntitlementTitleMeta (
    val imageUrl: String?,
    val name: String,
    val titleId: String
)