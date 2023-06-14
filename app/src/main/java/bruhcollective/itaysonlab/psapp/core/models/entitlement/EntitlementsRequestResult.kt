package bruhcollective.itaysonlab.psapp.core.models.entitlement

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EntitlementsRequestResult (
    val revisionId: Long?,
    val start: Int?,
    val totalResults: Int?,
    val entitlements: List<GameEntitlement>
)