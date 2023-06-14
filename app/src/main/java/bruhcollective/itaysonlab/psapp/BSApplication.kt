package bruhcollective.itaysonlab.psapp

import android.app.Application
import android.content.Context
import android.util.DisplayMetrics
import bruhcollective.itaysonlab.psapp.database.DatabaseHolder

class BSApplication: Application() {
    companion object {
        fun dp(dp: Float): Float {
            return dp * (applicationContext.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT).toFloat()
        }

        lateinit var applicationContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        Companion.applicationContext = applicationContext
        DatabaseHolder.init(applicationContext)
    }
}