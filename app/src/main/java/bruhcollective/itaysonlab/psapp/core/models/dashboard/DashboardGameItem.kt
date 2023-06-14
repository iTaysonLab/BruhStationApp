package bruhcollective.itaysonlab.psapp.core.models.dashboard

import android.os.Parcelable
import bruhcollective.itaysonlab.psapp.core.models.UnifiedGameItem
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class DashboardGameItem (
    val category: String,
    val titleId: String,
    val localizedImageUrl: String,
    val imageUrl: String,
    val playCount: Int,
    val firstPlayedDateTime: String,
    val lastPlayedDateTime: String,
    val playDuration: String,
    val service: String,
    val localizedName: String,
    val name: String,
    val media: DashboardGameMediaItems,
    val concept: DashboardGameConceptLink
): UnifiedGameItem, Parcelable {
    override fun getCusa() = titleId
    override fun getIconUrl() = localizedImageUrl
}