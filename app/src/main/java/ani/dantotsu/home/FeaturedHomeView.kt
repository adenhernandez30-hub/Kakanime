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
import ani.dantotsu.connections.anilist.AnilistHomeViewModel
import ani.dantotsu.loadImage
import ani.dantotsu.media.Media
import ani.dantotsu.media.MediaDetailsActivity
import com.google.android.material.button.MaterialButton
import kotlin.math.max

class FeaturedHomeView @JvmOverloads constructor(
    context: Context,
    attrs: android.util.AttributeSet? = null,
) : FrameLayout(context, attrs) {
    private val image = ImageView(context)
    private val title = TextView(context)
    private val description = TextView(context)
    private val metadata = TextView(context)
    private val watchNow = MaterialButton(context)
    private val addButton = MaterialButton(context)
    private val indicatorRow = LinearLayout(context)

    private var featured = emptyList<Media>()
    private var index = 0

    init {
        setPadding(0, dp(8), 0, dp(8))
        clipChildren = false
        setWillNotDraw(false)

        val contentFrame = FrameLayout(context)
        image.scaleType = ImageView.ScaleType.CENTER_CROP
        contentFrame.addView(image, FrameLayout.LayoutParams(-1, -1))

        contentFrame.addView(
            View(context).apply {
                background = GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    intArrayOf(0xEE04142D.toInt(), 0x44000000, 0x00000000)
                )
            },
            FrameLayout.LayoutParams(-1, -1)
        )

        contentFrame.addView(
            View(context).apply {
                background = GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    intArrayOf(0x00000000, 0xBD020A1A.toInt(), 0xF5030812.toInt())
                )
            },
            FrameLayout.LayoutParams(-1, -1)
        )

        val content = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.BOTTOM
            setPadding(dp(18), dp(18), dp(18), dp(18))
        }

        title.apply {
            textSize = 28f
            maxLines = 2
            setTextColor(Color.WHITE)
            typeface = ResourcesCompat.getFont(context, R.font.poppins_bold)
        }
        content.addView(title, LinearLayout.LayoutParams(-1, -2))

        description.apply {
            textSize = 12f
            maxLines = 2
            setTextColor(0xE6FFFFFF.toInt())
            typeface = ResourcesCompat.getFont(context, R.font.poppins)
        }
        content.addView(description, LinearLayout.LayoutParams(-1, -2).apply { topMargin = dp(6) })

        metadata.apply {
            textSize = 11f
            setTextColor(0xCCFFFFFF.toInt())
            typeface = ResourcesCompat.getFont(context, R.font.poppins_semi_bold)
        }
        content.addView(metadata, LinearLayout.LayoutParams(-1, -2).apply { topMargin = dp(8) })

        val actionRow = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }

        watchNow.apply {
            text = "Watch Now"
            textSize = 12f
            minHeight = dp(42)
            setPadding(dp(16), 0, dp(16), 0)
            cornerRadius = dp(20)
            setIconResource(R.drawable.ic_round_play_arrow_24)
            iconGravity = MaterialButton.ICON_GRAVITY_TEXT_START
        }
        actionRow.addView(watchNow, LinearLayout.LayoutParams(-2, dp(42)))

        addButton.apply {
            text = ""
            minHeight = dp(42)
            minimumHeight = dp(42)
            minimumWidth = dp(42)
            cornerRadius = dp(21)
            setIconResource(android.R.drawable.ic_input_add)
            iconGravity = MaterialButton.ICON_GRAVITY_TEXT_TOP
        }
        actionRow.addView(addButton, LinearLayout.LayoutParams(dp(42), dp(42)).apply {
            marginStart = dp(10)
        })

        content.addView(actionRow, LinearLayout.LayoutParams(-2, -2).apply { topMargin = dp(10) })

        indicatorRow.orientation = LinearLayout.HORIZONTAL
        content.addView(indicatorRow, LinearLayout.LayoutParams(-2, -2).apply { topMargin = dp(12) })

        contentFrame.addView(content, FrameLayout.LayoutParams(-1, -1))
        addView(contentFrame, LayoutParams(-1, dp(340)))
        post { bindModel() }
    }

    private fun bindModel() {
        val owner = findViewTreeLifecycleOwner() ?: return
        val activity = context as? FragmentActivity ?: return
        val model = ViewModelProvider(activity)[AnilistHomeViewModel::class.java]
        model.getPublicFeatured().observe(owner) { list ->
            if (!list.isNullOrEmpty()) {
                featured = list
                index = 0
                renderCurrent()
            }
        }
    }

    private fun renderCurrent() {
        val media = featured.getOrNull(index) ?: return
        image.loadImage(media.banner ?: media.cover)
        title.text = media.userPreferredName.ifBlank { media.nameRomaji }
        val text = media.description?.let {
            Html.fromHtml(it, Html.FROM_HTML_MODE_LEGACY).toString().trim()
        }
        description.text = text?.takeIf { it.isNotBlank() } ?: "Discover this anime on AniLab."

        val type = media.format?.replace('_', ' ')?.replaceFirstChar { it.titlecase() } ?: "Anime"
        val genres = media.genres?.take(3)?.joinToString(" • ").orEmpty()
        metadata.text = listOf(type, genres).filter { it.isNotBlank() }.joinToString(" • ")

        val open = View.OnClickListener {
            context.startActivity(
                Intent(context, MediaDetailsActivity::class.java).putExtra("media", media)
            )
        }
        watchNow.setOnClickListener(open)
        addButton.setOnClickListener(open)
        setOnClickListener(open)

        indicatorRow.removeAllViews()
        val dots = max(1, minOf(5, featured.size))
        repeat(dots) { position ->
            indicatorRow.addView(View(context).apply {
                background = GradientDrawable().apply {
                    shape = GradientDrawable.RECTANGLE
                    cornerRadius = dp(3).toFloat()
                    setColor(if (position == index % dots) Color.WHITE else 0x66FFFFFF)
                }
            }, LinearLayout.LayoutParams(if (position == index % dots) dp(20) else dp(10), dp(4)).apply {
                marginEnd = dp(6)
            })
        }
    }

    private fun dp(value: Int): Int = (value * resources.displayMetrics.density).toInt()
}
