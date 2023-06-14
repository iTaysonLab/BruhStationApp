package bruhcollective.itaysonlab.psapp.core.models.dashboard

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class DashboardGameImage (
    val url: String,
    val type: String
): Parcelable