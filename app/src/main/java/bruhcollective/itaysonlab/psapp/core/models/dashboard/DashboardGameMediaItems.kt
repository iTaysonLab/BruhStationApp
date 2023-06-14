package bruhcollective.itaysonlab.psapp.core.models.dashboard

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class DashboardGameMediaItems (
    val images: List<DashboardGameImage>
): Parcelable