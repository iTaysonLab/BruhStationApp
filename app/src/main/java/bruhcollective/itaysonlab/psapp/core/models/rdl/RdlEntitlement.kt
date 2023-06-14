package bruhcollective.itaysonlab.psapp.core.models.rdl

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RdlEntitlement(
    val entitlementId: String, // example: EP0006-CUSA08670_00-BATTLEFIELDV0000
    val platform: String, // ps4(5?)
    val playGoScenario: String? = null, // 1 (maybe 0 for force-disable)
    val status: String? = null
) {
    // status: notstarted, ..
}