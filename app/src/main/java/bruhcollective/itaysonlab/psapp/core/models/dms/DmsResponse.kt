package bruhcollective.itaysonlab.psapp.core.models.dms

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DmsResponse(
    val accountId: String,
    val accountDevices: List<DmsRegisteredDevice> = emptyList()
)