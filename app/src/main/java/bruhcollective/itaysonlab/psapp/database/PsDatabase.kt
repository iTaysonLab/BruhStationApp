package bruhcollective.itaysonlab.psapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import bruhcollective.itaysonlab.psapp.database.models.DbAuth
import bruhcollective.itaysonlab.psapp.database.models.DbAuthDao
import bruhcollective.itaysonlab.psapp.database.models.DbUser
import bruhcollective.itaysonlab.psapp.database.models.DbUserDao

@TypeConverters(PsDatabaseConverters::class)
@Database(entities = [DbUser::class, DbAuth::class], version = 1, exportSchema = true)
abstract class PsDatabase: RoomDatabase() {
    abstract fun user(): DbUserDao
    abstract fun auth(): DbAuthDao
}