package ani.dantotsu.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ani.dantotsu.databinding.ItemAccountMenuBinding

class AccountMenuAdapter : RecyclerView.Adapter<AccountMenuAdapter.VH>() {
    private val items = mutableListOf<AccountMenuUi>()

    fun submit(data: List<AccountMenuUi>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemAccountMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.binding.accountMenuTitle.text = item.title
        holder.binding.accountMenuDescription.text = item.description
    }

    override fun getItemCount(): Int = items.size

    class VH(val binding: ItemAccountMenuBinding) : RecyclerView.ViewHolder(binding.root)
}

data class AccountMenuUi(
    val title: String,
    val description: String,
)
