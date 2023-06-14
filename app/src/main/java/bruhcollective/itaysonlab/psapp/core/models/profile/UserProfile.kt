package bruhcollective.itaysonlab.psapp.core.models.profile

import androidx.room.Embedded
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserProfile(
    val onlineId: String,
    val isPlus: Boolean,
    val avatars: List<UserAvatar>,
    @Embedded val personalDetail: UserPersonalDetail,
)