package ani.dantotsu.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ani.dantotsu.R
import ani.dantotsu.databinding.ItemHomeContinueBinding
import ani.dantotsu.loadImage
import ani.dantotsu.media.Media
import ani.dantotsu.media.MediaDetailsActivity
import ani.dantotsu.setAnimation
import ani.dantotsu.setSafeOnClickListener
import java.io.Serializable

class HomeContinueAdapter(
    private val mediaList: List<Media>,
    private val activity: androidx.fragment.app.FragmentActivity
) : RecyclerView.Adapter<HomeContinueAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemHomeContinueBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val media = mediaList[position]
        val binding = holder.binding
        setAnimation(activity, binding.root)

        val progress = media.userProgress ?: 0
        val total = media.anime?.totalEpisodes ?: 0
        val nextEpisode = (progress + 1).coerceAtLeast(1)
        val thumbnail = media.streamingEpisodes
            ?.getOrNull(progress)
            ?.thumbnail
            ?: media.streamingEpisodes?.getOrNull(progress - 1)?.thumbnail
            ?: media.cover

        binding.homeContinueThumbnail.loadImage(thumbnail)
        binding.homeContinueTitle.text = media.userPreferredName
        binding.homeContinueEpisodeBadge.text = "EP $nextEpisode"
        binding.homeContinueProgressText.text = if (total > 0) {
            "Episode $progress / $total"
        } else {
            "Episode $progress"
        }

        binding.homeContinueProgress.max = 100
        binding.homeContinueProgress.progress =
            if (total > 0) ((progress * 100f) / total).toInt().coerceIn(0, 100) else 0

        val open = {
            ContextCompat.startActivity(
                activity,
                Intent(activity, MediaDetailsActivity::class.java).putExtra(
                    "media",
                    media as Serializable
                ),
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity,
                    binding.homeContinueThumbnail,
                    "mediaCover"
                ).toBundle()
            )
        }

        binding.root.setSafeOnClickListener { open() }
        binding.homeContinuePlay.setSafeOnClickListener { open() }
    }

    override fun getItemCount(): Int = mediaList.size

    class ViewHolder(
        val binding: ItemHomeContinueBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
