package ani.dantotsu.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ani.dantotsu.databinding.ItemSocialCardBinding

class SocialListAdapter : RecyclerView.Adapter<SocialListAdapter.VH>() {
    private val items = mutableListOf<SocialItemUi>()

    fun submit(data: List<SocialItemUi>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemSocialCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.binding.socialCardTitle.text = item.title
        holder.binding.socialCardDescription.text = item.description
    }

    override fun getItemCount(): Int = items.size

    class VH(val binding: ItemSocialCardBinding) : RecyclerView.ViewHolder(binding.root)
}

data class SocialItemUi(
    val title: String,
    val description: String,
)
