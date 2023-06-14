package bruhcollective.itaysonlab.psapp.drawer

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.viewpager.widget.ViewPager
import bruhcollective.itaysonlab.psapp.log.LoggingFacade
import kotlin.math.max

class SlidingViewPager @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : ViewPager(context, attrs) {
    var interceptor: () -> Boolean = { false }
    var onMenuOpenedListener: (Boolean) -> Unit = {}

    var isMenuOpened = false
    private set

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return interceptor() && super.onInterceptTouchEvent(ev)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return interceptor() && super.onTouchEvent(ev)
    }

    override fun onPageScrolled(position: Int, offset: Float, offsetPixels: Int) {
        super.onPageScrolled(position, offset, offsetPixels)
        isMenuOpened = position != 1 || offset != 0f
        onMenuOpenedListener(isMenuOpened)
    }
}