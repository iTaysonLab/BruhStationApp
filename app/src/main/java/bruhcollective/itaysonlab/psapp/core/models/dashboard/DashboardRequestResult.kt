package bruhcollective.itaysonlab.psapp.core.models.dashboard

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DashboardRequestResult (
    val nextOffset: Int?,
    val previousOffset: Int?,
    val totalItemCount: Int?,
    val titles: List<DashboardGameItem>
)