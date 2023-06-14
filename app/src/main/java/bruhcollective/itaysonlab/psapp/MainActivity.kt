package bruhcollective.itaysonlab.psapp

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.updatePadding
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import bruhcollective.itaysonlab.psapp.databinding.ActivityCoreBinding
import bruhcollective.itaysonlab.psapp.drawer.SlidingDrawer
import bruhcollective.itaysonlab.psapp.ui.WindowInsetHolder

class MainActivity : AppCompatActivity() {
    private val hideToolbarOn = setOf(R.id.navigation_landing)
    private val showNavOn = setOf(R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
    private val fullScreenContentOn = setOf(0)
    private val toolbarHeight by lazy { resources.getDimensionPixelSize(actionBarSize()) }

    private lateinit var binding: ActivityCoreBinding
    private lateinit var drawer: SlidingDrawer
    lateinit var backCallback: OnBackPressedCallback

    fun getTopInset() = actionBarSize() + WindowInsetHolder.top
    fun refreshSidebar() = drawer.reloadMenu()

    private fun actionBarSize(): Int {
        val value = TypedValue()
        theme.resolveAttribute(androidx.appcompat.R.attr.actionBarSize, value, true)
        return value.resourceId
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        backCallback = onBackPressedDispatcher.addCallback {
            drawer.close()
        }

        drawer = SlidingDrawer(this, binding.pager) { drawer, binding ->
            val toolbar: Toolbar = binding.toolbar
            val navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment).navController

            val appBarConfiguration = AppBarConfiguration(showNavOn, drawer)
            toolbar.setupWithNavController(navController, appBarConfiguration)

            navController.addOnDestinationChangedListener { controller, destination, arguments ->
                drawer.interceptSlidingDrawer = showNavOn.contains(destination.id)

                if (hideToolbarOn.contains(destination.id)) {
                    toolbar.visibility = View.GONE
                    binding.navHostFragmentActivityMain.layoutParams = (binding.navHostFragmentActivityMain.layoutParams as ViewGroup.MarginLayoutParams).also {
                        it.topMargin = 0
                    }
                } else {
                    toolbar.visibility = View.VISIBLE

                    if (!fullScreenContentOn.contains(destination.id)) {
                        binding.navHostFragmentActivityMain.layoutParams = (binding.navHostFragmentActivityMain.layoutParams as ViewGroup.MarginLayoutParams).also {
                            it.topMargin = toolbarHeight + WindowInsetHolder.top
                        }
                    }
                }
            }

            WindowInsetHolder.requestEdgeToEdge(window, this.binding.root) {
                drawer.applyInsets()
                binding.toolbar.updatePadding(top = WindowInsetHolder.top)
                binding.navHostFragmentActivityMain.layoutParams = (binding.navHostFragmentActivityMain.layoutParams as ViewGroup.MarginLayoutParams).also {
                    if (it.topMargin > 0) it.topMargin = toolbarHeight + WindowInsetHolder.top
                }
            }
        }
    }
}