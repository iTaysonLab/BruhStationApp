package bruhcollective.itaysonlab.psapp.database.models

import androidx.room.*

@Dao
interface DbUserDao {
    fun update(block: DbUser.() -> DbUser) = insert(get()!!.block())
    fun hasUser() = get() != null
    fun get() = getAll().getOrNull(0)

    @Query("SELECT * FROM user")
    fun getAll(): List<DbUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: DbUser)

    @Delete
    fun delete(user: DbUser)
}