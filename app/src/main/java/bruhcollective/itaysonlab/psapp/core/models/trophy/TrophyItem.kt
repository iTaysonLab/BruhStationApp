package bruhcollective.itaysonlab.psapp.core.models.trophy

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TrophyItem (
    val earned: Boolean,
    val earnedDateTime: String,
    val trophyIconUrl: String,
    val trophyId: Int,
    val trophyName: String,
    val trophyDetail: String,
    val trophyRare: Int,
    val trophyHidden: Boolean,
    val trophyType: String,
    val trophyEarnedRate: String,
)