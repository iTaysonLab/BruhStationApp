package bruhcollective.itaysonlab.psapp.core.models.trophy

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TrophyNpResult (
    val npTitleId: String,
    val trophyTitles: List<TrophyNpEntry>
)