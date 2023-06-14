package bruhcollective.itaysonlab.psapp.core.models.entitlement

import bruhcollective.itaysonlab.psapp.core.models.UnifiedGameItem
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GameEntitlement (
    val id: String,
    val activeDate: String,
    val activeFlag: Boolean,
    val preorderFlag: Boolean,
    val gameMeta: GameEntitlementMeta,
    val productId: String,
    val skuId: String,
    val conceptMeta: GameEntitlementConceptMeta? = null,
    val isSubscription: Boolean,
    val serviceType: Int?,
    val entitlementType: Int,
    val titleMeta: GameEntitlementTitleMeta,
    val featureType: Int,
): UnifiedGameItem {
    override fun getCusa() = titleMeta.titleId
    override fun getIconUrl() = titleMeta.imageUrl ?: gameMeta.iconUrl
}