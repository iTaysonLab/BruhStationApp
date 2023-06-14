package bruhcollective.itaysonlab.psapp.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import bruhcollective.itaysonlab.psapp.BSApplication
import bruhcollective.itaysonlab.psapp.R
import bruhcollective.itaysonlab.psapp.data.adapter.DashboardAdapter
import bruhcollective.itaysonlab.psapp.databinding.FragmentLibraryBinding
import bruhcollective.itaysonlab.psapp.drawer.VpReflection
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LibraryFragment : Fragment(R.layout.fragment_library) {
    private val binding: FragmentLibraryBinding by viewBinding(FragmentLibraryBinding::bind)
    private val viewModel: DashboardViewModel by viewModels()

    private val adapterRecent = DashboardAdapter(DashboardAdapter.DashboardComparator)
    private val adapterOwned = DashboardAdapter(DashboardAdapter.DashboardComparator)

    private val recyclerHolders by lazy {
        Array(2) {
            RecyclerView(requireContext()).also {
                it.layoutManager = GridLayoutManager(requireContext(), calculateNoOfColumns(requireContext()))
                it.addItemDecoration(SpacesItemDecoration(BSApplication.dp(12f).toInt()))
                it.isNestedScrollingEnabled = true
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerHolders[0].adapter = adapterRecent
        recyclerHolders[1].adapter = adapterOwned

        binding.pager.interceptor = { false }
        binding.pager.overScrollMode = View.OVER_SCROLL_NEVER
        binding.pager.adapter = object : PagerAdapter() {
            override fun getCount() = 2
            override fun isViewFromObject(view: View, `object`: Any) = view == `object`
            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) = container.removeView(`object` as RecyclerView)
            override fun getPageWidth(position: Int) = 1f
            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val v = recyclerHolders[position]
                container.addView(v)
                return v
            }
        }

        VpReflection.installSmoothScroll(binding.pager)

        binding.libraryChip.setOnClickListener {
            binding.libraryChip.isChecked = true
            binding.recentChip.isChecked = false
            binding.pager.setCurrentItem(1, true)
        }

        binding.recentChip.setOnClickListener {
            binding.libraryChip.isChecked = false
            binding.recentChip.isChecked = true
            binding.pager.setCurrentItem(0, true)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pagerRecentFlow.collectLatest { pagingData ->
                adapterRecent.submitData(pagingData)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pagerOwnedFlow.collectLatest { pagingData ->
                adapterOwned.submitData(pagingData)
            }
        }
    }

    private fun calculateNoOfColumns(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        val dpWidth: Float = displayMetrics.widthPixels / displayMetrics.density
        val scalingFactor = 200
        val columnCount = (dpWidth / scalingFactor).toInt()
        return if (columnCount >= 2) columnCount else 2
    }
}