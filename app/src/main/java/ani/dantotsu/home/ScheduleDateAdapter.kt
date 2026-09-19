package ani.dantotsu.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ani.dantotsu.databinding.ItemScheduleDateBinding

class ScheduleDateAdapter(
    private val items: List<ScheduleDateUi>,
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<ScheduleDateAdapter.VH>() {
    private var selectedIndex = 0

    fun setSelectedIndex(index: Int) {
        val previous = selectedIndex
        selectedIndex = index
        notifyItemChanged(previous)
        notifyItemChanged(index)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemScheduleDateBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.binding.scheduleDateDay.text = item.day
        holder.binding.scheduleDateNumber.text = item.date
        holder.binding.scheduleDateCard.isChecked = selectedIndex == position
        holder.binding.root.setOnClickListener { onClick(position) }
    }

    override fun getItemCount(): Int = items.size

    class VH(val binding: ItemScheduleDateBinding) : RecyclerView.ViewHolder(binding.root)
}

data class ScheduleDateUi(
    val day: String,
    val date: String,
    val isToday: Boolean,
)
