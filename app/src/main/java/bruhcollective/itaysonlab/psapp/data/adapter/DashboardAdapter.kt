package bruhcollective.itaysonlab.psapp.data.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import bruhcollective.itaysonlab.psapp.core.models.UnifiedGameItem
import bruhcollective.itaysonlab.psapp.core.models.dashboard.DashboardGameItem
import bruhcollective.itaysonlab.psapp.databinding.ItemScalableGridBinding
import bruhcollective.itaysonlab.psapp.ui.dialog.StatisticsSheet
import coil.load
import coil.transform.RoundedCornersTransformation

class DashboardAdapter(diffCallback: DiffUtil.ItemCallback<UnifiedGameItem>): PagingDataAdapter<UnifiedGameItem, DashboardAdapter.DashboardViewHolder>(diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DashboardViewHolder {
        return DashboardViewHolder(ItemScalableGridBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        // Note that item may be null. ViewHolder must support binding a
        // null item as a placeholder.
        holder.bind(getItem(position))
    }

    object DashboardComparator : DiffUtil.ItemCallback<UnifiedGameItem>() {
        override fun areItemsTheSame(oldItem: UnifiedGameItem, newItem: UnifiedGameItem): Boolean {
            return oldItem.getCusa() == newItem.getCusa()
        }

        override fun areContentsTheSame(oldItem: UnifiedGameItem, newItem: UnifiedGameItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    class DashboardViewHolder(private val binding: ItemScalableGridBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UnifiedGameItem?) {
            println(item.toString())

            item ?: return

            // media.images.first { it.type == "MASTER" }.url
            binding.plPhoto.load(item.getIconUrl()) {
                // TODO: placeholder
                placeholder(ColorDrawable(Color.parseColor("#212121")))
                size(600, 600)
            }

            binding.root.setOnClickListener {
                if (item is DashboardGameItem) {
                    StatisticsSheet.newInstance(Bundle().also {
                        it.putParcelable("gameItem", item)
                    }).show((binding.root.context as FragmentActivity).supportFragmentManager, null)
                }
            }

            /*binding.heroView.load(item.media.images.first { it.type == "GAMEHUB_COVER_ART" }.url)
            binding.title.text = item.name
            binding.info.text = "playCount = ${item.playCount}\nplayDuration = ${item.playDuration}\nfirstPlayedDateTime = ${item.firstPlayedDateTime}\nlastPlayedDateTime = ${item.lastPlayedDateTime}"*/
        }
    }
}