package bruhcollective.itaysonlab.psapp.database.models

import androidx.room.*

@Dao
interface DbAuthDao {
    fun update(block: DbAuth.() -> Unit) = insert(get()!!.also(block))
    fun hasUser() = get() != null
    fun get() = getAll().getOrNull(0)

    @Query("SELECT * FROM auth")
    fun getAll(): List<DbAuth>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: DbAuth)

    @Delete
    fun delete(user: DbAuth)
}