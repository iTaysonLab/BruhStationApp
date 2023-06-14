package bruhcollective.itaysonlab.psapp.database

import android.content.Context
import androidx.room.Room
import bruhcollective.itaysonlab.psapp.database.models.DbUser
import bruhcollective.itaysonlab.psapp.log.LoggingFacade
import kotlinx.coroutines.*

object DatabaseHolder: CoroutineScope by MainScope() {
    lateinit var database: PsDatabase

    fun getSyncDatabase() {
        LoggingFacade.logDebug("DatabaseHolder", "getSyncDatabase is deprecated due to thread blocking. Please use non-blocking ones")
        TODO()
    }

    // Func - main thread
    fun <T> getAsyncDatabase(dataFunc: PsDatabase.() -> T?, func: (T) -> Unit) {
        launch {
            func(withContext(Dispatchers.IO) { dataFunc(database) } ?: return@launch)
        }
    }

    // Helpers
    fun getAsyncCurrentUser(func: (DbUser) -> Unit) {
        getAsyncDatabase(dataFunc = { user().get() }, func)
    }

    fun init(ctx: Context) {
        database = Room.databaseBuilder(ctx.applicationContext, PsDatabase::class.java, "bruhstation").build()
    }
}