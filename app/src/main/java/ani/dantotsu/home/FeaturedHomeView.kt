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

class FeaturedHomeView @JvmOverloads constructor(context: Context, attrs: android.util.AttributeSet? = null) : FrameLayout(context, attrs) {
    private val image = ImageView(context)
    private val title = TextView(context)
    private val description = TextView(context)
    private val badge = TextView(context)
    private val action = MaterialButton(context)

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
                    intArrayOf(0xF0000000.toInt(), 0x18000000, 0x00000000)
                )
            },
            FrameLayout.LayoutParams(-1, -1)
        )

        contentFrame.addView(
            View(context).apply {
                background = GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    intArrayOf(0x00000000, 0xB8000000.toInt(), 0xF5000000.toInt())
                )
            },
            FrameLayout.LayoutParams(-1, -1)
        )

        val content = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.BOTTOM
            setPadding(dp(18), dp(18), dp(18), dp(18))
        }

        badge.apply {
            text = "FEATURED"
            textSize = 10f
            setTextColor(Color.WHITE)
            typeface = ResourcesCompat.getFont(context, R.font.poppins_bold)
            setPadding(dp(9), dp(4), dp(9), dp(4))
            background = GradientDrawable().apply {
                cornerRadius = dp(8).toFloat()
                setColor(0xAA000000.toInt())
            }
        }
        content.addView(badge, LinearLayout.LayoutParams(-2, -2).apply { bottomMargin = dp(8) })

        title.apply {
            textSize = 21f
            maxLines = 2
            setTextColor(Color.WHITE)
            typeface = ResourcesCompat.getFont(context, R.font.poppins_bold)
        }
        content.addView(title, LinearLayout.LayoutParams(-1, -2))

        description.apply {
            textSize = 11.5f
            maxLines = 2
            setTextColor(0xE6FFFFFF.toInt())
            typeface = ResourcesCompat.getFont(context, R.font.poppins)
        }
        content.addView(description, LinearLayout.LayoutParams(-1, -2).apply { topMargin = dp(4) })

        action.apply {
            text = "View Anime"
            textSize = 11f
            minHeight = dp(38)
            setPadding(dp(14), 0, dp(14), 0)
            cornerRadius = dp(14)
        }
        content.addView(action, LinearLayout.LayoutParams(-2, dp(38)).apply { topMargin = dp(10) })

        contentFrame.addView(content, FrameLayout.LayoutParams(-1, -1))
        addView(contentFrame, LayoutParams(-1, dp(300)))
        post { bindModel() }
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
        image.loadImage(media.banner ?: media.cover)
        title.text = media.userPreferredName.ifBlank { media.nameRomaji }
        val text = media.description?.let {
            Html.fromHtml(it, Html.FROM_HTML_MODE_LEGACY).toString().trim()
        }
        description.text = text?.takeIf { it.isNotBlank() } ?: "Discover this anime on AniLab."
        val open = View.OnClickListener {
            context.startActivity(
                Intent(context, MediaDetailsActivity::class.java).putExtra("media", media)
            )
        }
        action.setOnClickListener(open)
        setOnClickListener(open)
    }

    private fun dp(value: Int): Int = (value * resources.displayMetrics.density).toInt()
}
