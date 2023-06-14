package bruhcollective.itaysonlab.psapp.drawer

import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.customview.widget.Openable
import androidx.viewpager.widget.PagerAdapter
import bruhcollective.itaysonlab.psapp.MainActivity
import bruhcollective.itaysonlab.psapp.databinding.ActivityLeftMenuBinding
import bruhcollective.itaysonlab.psapp.databinding.ActivityMainBinding
import bruhcollective.itaysonlab.psapp.ui.WindowInsetHolder

class SlidingDrawer(private val activity: MainActivity, private val pager: SlidingViewPager, initApp: (SlidingDrawer, ActivityMainBinding) -> Unit): Openable {
    private val appView: ActivityMainBinding
    private val menuView: ActivityLeftMenuBinding
    private val rmController: RightMenuController

    var interceptSlidingDrawer: Boolean = true

    fun reloadMenu() {
        rmController.reload()
    }
    
    fun applyInsets() {
        menuView.root.setPadding(0, WindowInsetHolder.top, 0, 0)
    }

    init {
        appView = ActivityMainBinding.inflate(activity.layoutInflater, pager, false).also { initApp(this, it) }
        menuView = ActivityLeftMenuBinding.inflate(activity.layoutInflater, pager, false)
        rmController = RightMenuController(menuView)

        pager.interceptor = { interceptSlidingDrawer }
        pager.onMenuOpenedListener = {
            activity.backCallback.isEnabled = it
        }

        pager.overScrollMode = View.OVER_SCROLL_NEVER
        pager.adapter = object: PagerAdapter() {
            override fun getCount() = 2
            override fun isViewFromObject(view: View, `object`: Any) = view == `object`
            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) = container.removeView(`object` as ConstraintLayout)
            override fun getPageWidth(position: Int) = if (position == 1) 1f else 0.7f
            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val v = (if (position == 1) appView else menuView)
                container.addView(v.root)
                return v.root
            }
        }
        pager.setCurrentItem(1, false)

        VpReflection.installSmoothScroll(pager)
    }

    override fun isOpen() = pager.currentItem == 0
    override fun open() = pager.setCurrentItem(0, true)
    override fun close() = pager.setCurrentItem(1, true)
}