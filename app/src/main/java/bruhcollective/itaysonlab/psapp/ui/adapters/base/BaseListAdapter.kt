package bruhcollective.itaysonlab.psapp.ui.adapters.base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import bruhcollective.itaysonlab.psapp.core.models.MarkableWithId

abstract class BaseListAdapter<Item, Binding : ViewBinding> :
    ListAdapter<Item, BaseListAdapter<Item, Binding>.ListHolder>(object : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item) = oldItem.getId() == newItem.getId()

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Item, newItem: Item) = oldItem == newItem

        private fun Any?.getId(): String = if (this is MarkableWithId) getItemId() else hashCode().toString()
    }) {

    private lateinit var layoutInflater: LayoutInflater

    inner class ListHolder(val binding: Binding) : RecyclerView.ViewHolder(binding.root)

    abstract fun createBinding(inflater: LayoutInflater, parent: ViewGroup): Binding
    abstract fun bindHolder(position: Int, item: Item, binding: Binding)

    abstract fun getLayoutId(): Int
    override fun getItemViewType(position: Int) = getLayoutId()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        if (!::layoutInflater.isInitialized) layoutInflater = LayoutInflater.from(parent.context)
        return ListHolder(createBinding(layoutInflater, parent))
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) =
        bindHolder(position, getItem(position), holder.binding)
}