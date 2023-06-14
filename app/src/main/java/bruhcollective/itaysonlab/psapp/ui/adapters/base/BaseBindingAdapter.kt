package bruhcollective.itaysonlab.psapp.ui.adapters.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseBindingAdapter<Binding : ViewBinding> : RecyclerView.Adapter<BaseBindingAdapter<Binding>.Holder>() {
    private lateinit var layoutInflater: LayoutInflater

    inner class Holder(val binding: Binding) : RecyclerView.ViewHolder(binding.root)

    abstract fun createBinding(inflater: LayoutInflater, parent: ViewGroup): Binding
    abstract fun bindHolder(index: Int, binding: Binding)

    abstract fun getLayoutId(): Int
    override fun getItemViewType(position: Int) = getLayoutId()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        if (!::layoutInflater.isInitialized) layoutInflater = LayoutInflater.from(parent.context)
        return Holder(createBinding(layoutInflater, parent))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) = bindHolder(position, holder.binding)
}