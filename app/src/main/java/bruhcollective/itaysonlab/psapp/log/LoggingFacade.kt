package bruhcollective.itaysonlab.psapp.log

import android.util.Log

object LoggingFacade {
    @JvmStatic
    fun logDebug(tag: String, msg: String) {
        Log.d("BSA::$tag", msg)
    }

    @JvmStatic
    fun logDebug(tag: String, msg: String, vararg formatArgs: Any?) {
        Log.d("BSA::$tag", String.format(msg, formatArgs))
    }
}