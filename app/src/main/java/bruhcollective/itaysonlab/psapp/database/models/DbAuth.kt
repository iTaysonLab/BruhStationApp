package bruhcollective.itaysonlab.psapp.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "auth")
data class DbAuth(
    @PrimaryKey val id: Int,
    var oauthCode: String,
    var oauthCid: String,
    var accountToken: String,
    var accountTokenExpiresIn: Long,
    var refreshToken: String,
    var refreshTokenExpiresIn: Long,
    var idToken: String,
    var npsso: String,
    var tokenAcqTime: Long,
    var refreshTokenAcqTime: Long
)