package bruhcollective.itaysonlab.psapp.core.models.trophy

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TrophyRequestResult (
    val titles: TrophyNpResult
)