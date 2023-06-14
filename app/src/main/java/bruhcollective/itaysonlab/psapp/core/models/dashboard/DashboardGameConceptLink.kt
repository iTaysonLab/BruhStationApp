package bruhcollective.itaysonlab.psapp.core.models.dashboard

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class DashboardGameConceptLink (
    val id: Int = 0,
    val genres: List<String> = emptyList(),
    val subGenres: List<String> = emptyList(),
    val name: String = "",
): Parcelable