package bruhcollective.itaysonlab.psapp.database

import androidx.room.TypeConverter
import bruhcollective.itaysonlab.psapp.core.models.dms.DmsRegisteredDevice
import bruhcollective.itaysonlab.psapp.core.models.profile.UserAvatar
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class PsDatabaseConverters {
    private val moshi = Moshi.Builder().build()
    private val avatarAdapter: JsonAdapter<List<UserAvatar>> = moshi.adapter(Types.newParameterizedType(List::class.java, UserAvatar::class.java))
    private val devAdapter: JsonAdapter<List<DmsRegisteredDevice>> = moshi.adapter(Types.newParameterizedType(List::class.java, DmsRegisteredDevice::class.java))

    @TypeConverter
    fun listUserAvatarToJsonStr(list: List<UserAvatar>?): String? {
        return avatarAdapter.toJson(list)
    }

    @TypeConverter
    fun jsonStrToListUserAvatar(jsonStr: String?): List<UserAvatar>? {
        return jsonStr?.let { avatarAdapter.fromJson(jsonStr) }
    }

    @TypeConverter
    fun listDeviceToJsonStr(list: List<DmsRegisteredDevice>?): String? {
        return devAdapter.toJson(list)
    }

    @TypeConverter
    fun jsonStrToListDevice(jsonStr: String?): List<DmsRegisteredDevice>? {
        return jsonStr?.let { devAdapter.fromJson(jsonStr) }
    }
}