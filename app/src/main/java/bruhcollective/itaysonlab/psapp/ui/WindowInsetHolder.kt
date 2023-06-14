package bruhcollective.itaysonlab.psapp.ui

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.widget.EditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import bruhcollective.itaysonlab.psapp.log.LoggingFacade

object WindowInsetHolder {
    private var isFullscreen = false

    var top: Int = 0
    var bottom: Int = 0
    var left: Int = 0
    var right: Int = 0

    fun apply(insets: WindowInsetsCompat) {
        top = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
        bottom = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
        left = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).left
        right = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).right
        LoggingFacade.logDebug("WindowInsetHolder", "[top] $top / [bottom] $bottom / [left] $left / [right] $right")
    }

    fun requestEdgeToEdge(window: Window, nav: View, callback: (WindowInsetsCompat) -> Unit) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT

        ViewCompat.setOnApplyWindowInsetsListener(nav) { v, insets ->
            apply(insets)
            callback(insets)
            WindowInsetsCompat.CONSUMED
        }
    }

    fun setEdgeToEdge(window: Window) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT
    }

    // TODO: Maybe SYSTEM_UI_FLAG_LOW_PROFILE?

    fun toggleNavigationBars(window: Window, value: Boolean) {
        if (value) {
            showBars(window, WindowInsetsCompat.Type.navigationBars())
        } else {
            hideBars(window, WindowInsetsCompat.Type.navigationBars())
        }
    }

    fun toggleStatusBars(window: Window, value: Boolean) {
        if (isFullscreen == value) return
        isFullscreen = value
        if (value) {
            showBars(window, WindowInsetsCompat.Type.statusBars())
        } else {
            hideBars(window, WindowInsetsCompat.Type.statusBars())
        }
    }

    fun showBars(window: Window, bars: Int) {
        requestWindowController(window)?.show(bars)
    }

    fun hideBars(window: Window, bars: Int) {
        requestWindowController(window)?.hide(bars)
    }

    fun showImeAtView(window: Window, et: EditText) {
        requestWindowControllerWithImeSupport(window, et)?.show(WindowInsetsCompat.Type.ime())
    }

    fun setLightSystemAppearance(window: Window, light: Boolean) {
        if (light) setLightUI(window) else removeLightUI(window)
    }

    @Suppress("DEPRECATION")
    fun setLightUI(window: Window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var flags = window.decorView.systemUiVisibility
            if (flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR != View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) {
                flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    flags = flags or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                }
                window.decorView.systemUiVisibility = flags
            }
        }
    }

    @Suppress("DEPRECATION")
    fun removeLightUI(window: Window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var flags = window.decorView.systemUiVisibility
            if (flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR == View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) {
                flags = flags xor View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    flags = flags xor View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                }
                window.decorView.systemUiVisibility = flags
            }
        }
    }

    private fun requestWindowControllerWithImeSupport(window: Window, et: EditText) = WindowCompat.getInsetsController(window, et)
    private fun requestWindowController(activity: Activity) = WindowCompat.getInsetsController(activity.window, activity.window.decorView)
    private fun requestWindowController(window: Window) = WindowCompat.getInsetsController(window, window.decorView)
}