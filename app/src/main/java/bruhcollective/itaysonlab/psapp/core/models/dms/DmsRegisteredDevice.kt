package bruhcollective.itaysonlab.psapp.core.models.dms

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DmsRegisteredDevice(
    val deviceId: String,
    val deviceName: String?,
    val deviceType: String,
    val activationType: String,
    val activationDate: String,
    val accountDeviceVector: String
)