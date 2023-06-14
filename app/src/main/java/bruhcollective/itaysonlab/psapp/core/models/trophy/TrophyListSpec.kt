package bruhcollective.itaysonlab.psapp.core.models.trophy

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TrophyListSpec (
    val bronze: Int,
    val silver: Int,
    val gold: Int,
    val platinum: Int
)