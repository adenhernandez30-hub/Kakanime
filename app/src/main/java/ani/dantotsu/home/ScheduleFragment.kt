package ani.dantotsu.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ani.dantotsu.databinding.FragmentScheduleBinding
import ani.dantotsu.navBarHeight
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ScheduleFragment : Fragment() {
    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!

    private val timelineAdapter = ScheduleTimelineAdapter()
    private lateinit var dateAdapter: ScheduleDateAdapter

    private val baseEpisodes = listOf(
        ScheduleEpisodeUi("18:00", "Sousou no Frieren", "Episode 28", "TV • PG-13 • 24m", "Adventure • Drama", "Aired", null, "https://s4.anilist.co/file/anilistcdn/media/anime/cover/large/bx154587-eJk6rXn8R5Ps.jpg"),
        ScheduleEpisodeUi("20:30", "Yani Neko", "Episode 12", "TV • R • 23m", "Comedy • Slice of Life", "Aired", null, "https://s4.anilist.co/file/anilistcdn/media/anime/cover/large/bx16498-buvcRTBx4NSQ.jpg"),
        ScheduleEpisodeUi("22:00", "Otome Kaijuu", "Episode 9", "ONA • PG-13 • 25m", "Fantasy • Romance", "Airing Soon", "1h 22m", "https://s4.anilist.co/file/anilistcdn/media/anime/cover/large/bx1735-cxM5iQw4HC8C.jpg"),
        ScheduleEpisodeUi("23:30", "Chiikawa", "Episode 37", "TV • G • 12m", "Comedy • Family", "Airing Soon", "2h 46m", "https://s4.anilist.co/file/anilistcdn/media/anime/cover/large/bx143327-W8M2L6rQ6BvJ.jpg")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.scheduleContainer.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            bottomMargin = navBarHeight
        }

        val dayFormat = SimpleDateFormat("EEE", Locale.getDefault())
        val dateFormat = SimpleDateFormat("dd", Locale.getDefault())
        val days = (0..6).map { offset ->
            val date = Calendar.getInstance().apply { add(Calendar.DATE, offset) }.time
            ScheduleDateUi(
                day = dayFormat.format(date),
                date = dateFormat.format(date),
                isToday = offset == 0
            )
        }

        dateAdapter = ScheduleDateAdapter(days) { index -> renderForDate(index, days[index]) }
        binding.scheduleDateRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.scheduleDateRecycler.adapter = dateAdapter

        binding.scheduleEpisodeRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.scheduleEpisodeRecycler.adapter = timelineAdapter

        renderForDate(0, days.first())
    }

    private fun renderForDate(index: Int, selected: ScheduleDateUi) {
        dateAdapter.setSelectedIndex(index)
        val list = baseEpisodes.mapIndexed { itemIndex, item ->
            val countdown = if (selected.isToday) item.countdown else null
            item.copy(
                countdown = countdown,
                status = if (selected.isToday) item.status else "Aired",
                episode = "Episode ${(itemIndex + 1) * (index + 1)}"
            )
        }
        binding.scheduleDayLabel.text = if (selected.isToday) "Today" else "${selected.day}, ${selected.date}"
        binding.scheduleCountLabel.text = "${list.size} Episodes"
        timelineAdapter.submit(list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
