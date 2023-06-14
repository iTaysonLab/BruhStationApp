package bruhcollective.itaysonlab.psapp.drawer

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import bruhcollective.itaysonlab.psapp.R
import bruhcollective.itaysonlab.psapp.database.DatabaseHolder
import bruhcollective.itaysonlab.psapp.databinding.ActivityLeftMenuBinding
import bruhcollective.itaysonlab.psapp.databinding.ItemLmHeaderBinding
import bruhcollective.itaysonlab.psapp.databinding.ItemLmItemBinding
import bruhcollective.itaysonlab.psapp.log.LoggingFacade
import bruhcollective.itaysonlab.psapp.ui.adapters.base.BaseBindingAdapter
import bruhcollective.itaysonlab.psapp.ui.adapters.base.BaseListAdapter
import coil.load
import coil.transform.CircleCropTransformation

class RightMenuController(
    binding: ActivityLeftMenuBinding
) {
    private val headerAdapter: HeaderAdapter

    @SuppressLint("NotifyDataSetChanged") // full rebind
    fun reload() {
        headerAdapter.notifyDataSetChanged()
    }

    enum class Items(
        @DrawableRes val icon: Int, val title: String
    ) {
        Library(R.drawable.round_library_books_24, "Library"),
        Store(R.drawable.round_store_24, "Store"),
        Wishlist(R.drawable.round_favorite_24, "Wishlist"),
        Explore(R.drawable.round_explore_24, "Explore"),
        Search(R.drawable.round_search_24, "Search"),
        Settings(R.drawable.round_settings_24, "Settings"),
    }

    init {
        headerAdapter = HeaderAdapter()
        binding.container.layoutManager = LinearLayoutManager(binding.container.context)
        binding.container.adapter = ConcatAdapter(headerAdapter, ItemAdapter().also { it.submitList(Items.values().toList()) })
    }

    inner class HeaderAdapter: BaseBindingAdapter<ItemLmHeaderBinding>() {
        override fun createBinding(
            inflater: LayoutInflater,
            parent: ViewGroup
        ) = ItemLmHeaderBinding.inflate(inflater, parent, false)

        override fun getLayoutId() = R.layout.item_lm_header
        override fun getItemCount() = 1

        override fun bindHolder(index: Int, binding: ItemLmHeaderBinding) {
            DatabaseHolder.getAsyncCurrentUser { user ->
                LoggingFacade.logDebug("RMC", "avatars = ${user.profile.avatars}")

                binding.avatar.load(user.profile.avatars.first { it.size == "m" }.url) {
                    error(GradientDrawable().also {
                        it.setColor(Color.parseColor("#212121"))
                        it.cornerRadius = 32f
                    })

                    placeholder(GradientDrawable().also {
                        it.setColor(Color.parseColor("#212121"))
                        it.cornerRadius = 32f
                    })

                    transformations(CircleCropTransformation())

                    listener(onError = { _, t ->
                        t.throwable.printStackTrace()
                    })
                }

                binding.username.text = "${user.profile.personalDetail.firstName} ${user.profile.personalDetail.lastName}"
                binding.onlineid.text = "${user.profile.onlineId}"
            }
        }
    }

    inner class ItemAdapter: BaseListAdapter<Items, ItemLmItemBinding>() {
        override fun createBinding(inflater: LayoutInflater, parent: ViewGroup) = ItemLmItemBinding.inflate(inflater, parent, false)
        override fun getLayoutId() = R.layout.item_lm_item

        override fun bindHolder(position: Int, item: Items, binding: ItemLmItemBinding) {
            binding.icon.setImageResource(item.icon)
            binding.title.text = item.title
            binding.root.setOnClickListener {

            }
        }
    }
}