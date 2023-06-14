package bruhcollective.itaysonlab.psapp.core.models.entitlement

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GameEntitlementMeta (
    val packageType: String,
    val iconUrl: String,
    val name: String,
    val type: String
)