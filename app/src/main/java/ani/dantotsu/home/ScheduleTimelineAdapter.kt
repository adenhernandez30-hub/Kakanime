package ani.dantotsu.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ani.dantotsu.databinding.ItemScheduleEpisodeBinding
import ani.dantotsu.loadImage

class ScheduleTimelineAdapter : RecyclerView.Adapter<ScheduleTimelineAdapter.VH>() {
    private val items = mutableListOf<ScheduleEpisodeUi>()

    fun submit(data: List<ScheduleEpisodeUi>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemScheduleEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        with(holder.binding) {
            scheduleEpisodeTime.text = item.time
            scheduleEpisodeTitle.text = item.title
            scheduleEpisodeNumber.text = item.episode
            scheduleEpisodeMeta.text = item.meta
            scheduleEpisodeGenre.text = item.genre
            scheduleEpisodeStatus.text = item.status
            scheduleEpisodeCountdown.text = item.countdown ?: ""
            scheduleEpisodeCountdown.visibility = if (item.countdown.isNullOrBlank()) View.GONE else View.VISIBLE
            scheduleEpisodePoster.loadImage(item.poster)
            scheduleEpisodeStatus.isChecked = item.status.equals("Aired", true)
        }
    }

    override fun getItemCount(): Int = items.size

    class VH(val binding: ItemScheduleEpisodeBinding) : RecyclerView.ViewHolder(binding.root)
}

data class ScheduleEpisodeUi(
    val time: String,
    val title: String,
    val episode: String,
    val meta: String,
    val genre: String,
    val status: String,
    val countdown: String?,
    val poster: String,
)
