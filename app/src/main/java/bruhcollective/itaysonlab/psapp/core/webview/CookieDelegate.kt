package bruhcollective.itaysonlab.psapp.core.webview
import android.webkit.CookieManager

object CookieDelegate {
    private val manager = CookieManager.getInstance()

    fun getCookies(url: String): String {
        return manager.getCookie(url)
    }

    fun requestNpsso(): String {
        return getCookies("https://ca.account.sony.com").split(";").associate {
            val s = it.split("=")
            Pair(s[0].removePrefix(" "), s[1])
        }["npsso"] ?: error("")
    }
}