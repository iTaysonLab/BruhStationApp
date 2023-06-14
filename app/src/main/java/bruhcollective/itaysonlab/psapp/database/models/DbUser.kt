package bruhcollective.itaysonlab.psapp.database.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import bruhcollective.itaysonlab.psapp.core.models.dms.DmsRegisteredDevice
import bruhcollective.itaysonlab.psapp.core.models.profile.UserProfile

@Entity(tableName = "user")
data class DbUser(
    @PrimaryKey val accountId: String,
    @Embedded var profile: UserProfile,
    val registeredDevices: List<DmsRegisteredDevice>
)