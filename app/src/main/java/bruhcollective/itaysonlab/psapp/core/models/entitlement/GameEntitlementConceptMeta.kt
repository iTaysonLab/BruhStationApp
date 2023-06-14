package bruhcollective.itaysonlab.psapp.core.models.entitlement

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GameEntitlementConceptMeta (
    val conceptId: Int = 0,
    val iconUrl: String? = null,
    val name: String = "",
    val minimumAge: Int = 0
)