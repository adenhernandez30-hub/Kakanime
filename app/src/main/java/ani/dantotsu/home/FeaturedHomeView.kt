package ani.dantotsu.home

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.text.Html
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeLifecycleOwner
import ani.dantotsu.R
import ani.dantotsu.blurImage
import ani.dantotsu.connections.anilist.AnilistHomeViewModel
import ani.dantotsu.loadImage
import ani.dantotsu.media.Media
import ani.dantotsu.media.MediaDetailsActivity
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.ShapeAppearanceModel

class FeaturedHomeView @JvmOverloads constructor(
    context: Context,
    attrs: android.util.AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val background = ImageView(context)
    private val poster = ShapeableImageView(context)
    private val title = TextView(context)
    private val description = TextView(context)
    private val status = TextView(context)
    private val meta = TextView(context)
    private val score = TextView(context)
    private val badge = TextView(context)

    private var currentMedia: Media? = null

    init {
        clipChildren = false
        setBackgroundColor(0xFF080A10.toInt())

        background.scaleType = ImageView.ScaleType.CENTER_CROP
        addView(background, LayoutParams(-1, dp(330)))

        addView(View(context).apply {
            setBackgroundColor(0x55000000)
        }, LayoutParams(-1, dp(330)))

        addView(View(context).apply {
            background = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                intArrayOf(
                    0xD9080A10.toInt(),
                    0x30080A10,
                    0xEE080A10.toInt()
                )
            )
        }, LayoutParams(-1, dp(330)))

        val row = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(dp(28), dp(16), dp(20), dp(16))
        }

        poster.apply {
            scaleType = ImageView.ScaleType.CENTER_CROP
            shapeAppearanceModel = ShapeAppearanceModel.builder()
                .setAllCornerSizes(dp(18).toFloat())
                .build()
            elevation = dp(3).toFloat()
        }
        row.addView(poster, LinearLayout.LayoutParams(dp(148), dp(220)).apply {
            rightMargin = dp(18)
        })

        val info = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_VERTICAL
        }

        badge.apply {
            text = "FEATURED"
            textSize = 9f
            setTextColor(Color.WHITE)
            typeface = ResourcesCompat.getFont(context, R.font.poppins_bold)
            setPadding(dp(9), dp(4), dp(9), dp(4))
            background = GradientDrawable().apply {
                cornerRadius = dp(8).toFloat()
                setColor(0xB8000000.toInt())
            }
        }
        info.addView(badge, LinearLayout.LayoutParams(-2, -2).apply {
            bottomMargin = dp(7)
        })

        title.apply {
            textSize = 17f
            maxLines = 2
            ellipsize = android.text.TextUtils.TruncateAt.END
            setTextColor(Color.WHITE)
            typeface = ResourcesCompat.getFont(context, R.font.poppins_bold)
        }
        info.addView(title, LinearLayout.LayoutParams(-1, -2))

        description.apply {
            textSize = 10.5f
            maxLines = 3
            ellipsize = android.text.TextUtils.TruncateAt.END
            setTextColor(0xD9FFFFFF.toInt())
            typeface = ResourcesCompat.getFont(context, R.font.poppins)
        }
        info.addView(description, LinearLayout.LayoutParams(-1, -2).apply {
            topMargin = dp(5)
        })

        status.apply {
            textSize = 10f
            setTextColor(0xFFE3A8FF.toInt())
            typeface = ResourcesCompat.getFont(context, R.font.poppins_bold)
        }
        info.addView(status, LinearLayout.LayoutParams(-1, -2).apply {
            topMargin = dp(7)
        })

        meta.apply {
            textSize = 9.5f
            maxLines = 1
            ellipsize = android.text.TextUtils.TruncateAt.END
            setTextColor(0xD9FFFFFF.toInt())
            typeface = ResourcesCompat.getFont(context, R.font.poppins)
        }
        info.addView(meta, LinearLayout.LayoutParams(-1, -2).apply {
            topMargin = dp(5)
        })

        score.apply {
            textSize = 9.5f
            setTextColor(0xFFFFD5FF.toInt())
            typeface = ResourcesCompat.getFont(context, R.font.poppins_bold)
            gravity = Gravity.CENTER
            setPadding(dp(7), dp(3), dp(7), dp(3))
            background = GradientDrawable().apply {
                cornerRadius = dp(9).toFloat()
                setColor(0xCC8E4BA0.toInt())
            }
        }
        info.addView(score, LinearLayout.LayoutParams(-2, -2).apply {
            topMargin = dp(7)
        })

        row.addView(info, LinearLayout.LayoutParams(0, -2, 1f))
        addView(row, LayoutParams(-1, dp(330)))

        setOnClickListener { currentMedia?.let(::open) }
        poster.setOnClickListener { currentMedia?.let(::open) }
    }

    private fun bindModel() {
        val owner = findViewTreeLifecycleOwner() ?: return
        val activity = context as? FragmentActivity ?: return
        val model = ViewModelProvider(activity)[AnilistHomeViewModel::class.java]
        model.getPublicFeatured().observe(owner) { list ->
            list?.randomOrNull()?.let(::render)
        }
    }

    private fun render(media: Media) {
        currentMedia = media
        val artwork = media.banner ?: media.cover
        background.loadImage(artwork)
        blurImage(background, artwork)
        poster.loadImage(media.cover ?: artwork)

        title.text = media.userPreferredName.ifBlank { media.nameRomaji }

        val rawDescription = media.description?.let {
            Html.fromHtml(it, Html.FROM_HTML_MODE_LEGACY).toString().trim()
        }
        description.text = rawDescription?.takeIf { it.isNotBlank() }
            ?: "Discover this anime on AniLab."

        status.text = when (media.status?.uppercase()) {
            "RELEASING" -> "RELEASING"
            "FINISHED" -> "FINISHED"
            "NOT_YET_RELEASED" -> "UPCOMING"
            else -> media.status?.uppercase() ?: "FEATURED"
        }

        val total = media.anime?.totalEpisodes ?: 0
        val progress = media.userProgress ?: 0
        val episodeText = if (total > 0) {
            progress.coerceAtLeast(0).toString() + " / " + total + " Episodes"
        } else {
            null
        }
        val genreText = media.genres.take(2).joinToString(" • ")
        meta.text = listOfNotNull(
            episodeText,
            genreText.takeIf { it.isNotBlank() }
        ).joinToString("    ")

        score.text = media.meanScore?.let { (it / 10f).toString() + " ★" } ?: ""
        score.visibility = if (media.meanScore != null) View.VISIBLE else View.GONE
    }

    private fun open(media: Media) {
        context.startActivity(
            Intent(context, MediaDetailsActivity::class.java)
                .putExtra("media", media)
        )
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        post { bindModel() }
    }

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()
}
