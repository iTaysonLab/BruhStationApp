package bruhcollective.itaysonlab.psapp.core.models.trophy

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TrophyNpEntry (
    val trophyTitleName: String,
    val trophyTitleDetail: String,
    val npServiceName: String,
    val progress: Int,
    val hasTrophyGroups: Boolean,
    val lastUpdatedDateTime: String,
    val npCommunicationId: String,
    val trophyTitleIconUrl: String,
    val rarestTrophies: List<TrophyItem>,
    val earnedTrophies: TrophyListSpec,
    val definedTrophies: TrophyListSpec,
)