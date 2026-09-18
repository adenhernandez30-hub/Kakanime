package ani.dantotsu

import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.min

class SplashActivity : AppCompatActivity() {

    private var animator: ValueAnimator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = Color.rgb(2, 10, 22)
        window.navigationBarColor = Color.rgb(2, 10, 22)

        setContentView(AniLabSplashView())
    }

    override fun onDestroy() {
        animator?.cancel()
        super.onDestroy()
    }

    private inner class AniLabSplashView : View(this@SplashActivity) {
        private val density = resources.displayMetrics.density
        private val bg = Color.rgb(2, 10, 22)
        private val electricBlue = Color.rgb(72, 191, 255)
        private val iceBlue = Color.rgb(216, 247, 255)
        private val stars = arrayOf(
            0.08f to 0.16f, 0.18f to 0.29f, 0.29f to 0.12f, 0.41f to 0.23f,
            0.57f to 0.11f, 0.71f to 0.22f, 0.90f to 0.14f, 0.14f to 0.61f,
            0.32f to 0.78f, 0.69f to 0.68f, 0.84f to 0.51f, 0.93f to 0.77f
        )
        private var progress = 0f

        init {
            setLayerType(LAYER_TYPE_SOFTWARE, null)
            animator = ValueAnimator.ofFloat(0f, 1f).apply {
                duration = 1800L
                repeatCount = ValueAnimator.INFINITE
                interpolator = LinearInterpolator()
                addUpdateListener {
                    progress = it.animatedValue as Float
                    invalidate()
                }
                start()
            }
            postDelayed({
                if (!isFinishing && !isDestroyed) {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }
            }, 2300L)
        }

        private fun dp(value: Float) = value * density

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)

            val w = width.toFloat()
            val h = height.toFloat()
            val cx = w / 2f
            val cy = h / 2f - dp(12f)
            val markSize = min(w, h) * 0.34f
            val pulse = 0.96f + 0.08f * ((kotlin.math.sin(progress * Math.PI * 2.0) + 1.0) / 2.0).toFloat()

            canvas.drawColor(bg)

            val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                shader = RadialGradient(
                    cx, cy, dp(420f),
                    intArrayOf(Color.rgb(10, 42, 73), bg, Color.rgb(1, 5, 12)),
                    floatArrayOf(0f, 0.58f, 1f),
                    Shader.TileMode.CLAMP
                )
            }
            canvas.drawRect(0f, 0f, w, h, bgPaint)

            val starPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL }
            stars.forEachIndexed { index, pair ->
                starPaint.color = Color.argb(
                    (255 * (0.35f + (index % 3) * 0.16f)).toInt(),
                    157, 227, 255
                )
                canvas.drawCircle(
                    w * pair.first,
                    h * pair.second,
                    if (index % 4 == 0) dp(2.4f) else dp(1.2f),
                    starPaint
                )
            }

            val glowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                shader = RadialGradient(
                    cx, cy, dp(165f),
                    intArrayOf(Color.argb(110, 8, 125, 219), Color.TRANSPARENT),
                    null,
                    Shader.TileMode.CLAMP
                )
            }
            canvas.drawCircle(cx, cy, dp(165f), glowPaint)

            drawMark(canvas, cx, cy, markSize, pulse)

            val titlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = Color.WHITE
                textSize = dp(39f)
                typeface = Typeface.create("sans-serif", Typeface.BOLD)
                textAlign = Paint.Align.CENTER
                alpha = 235
            }
            canvas.drawText("AniLab", cx, cy + markSize / 2f + dp(54f), titlePaint)

            val barWidth = dp(174f)
            val barHeight = dp(5f)
            val barLeft = cx - barWidth / 2f
            val barTop = cy + markSize / 2f + dp(82f)
            val barPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = Color.argb(33, 255, 255, 255)
            }
            canvas.drawRoundRect(
                barLeft, barTop, barLeft + barWidth, barTop + barHeight,
                dp(20f), dp(20f), barPaint
            )

            val shimmerX = barLeft + (progress * barWidth * 1.4f) - barWidth * 0.4f
            val shimmerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                shader = LinearGradient(
                    shimmerX - dp(70f), 0f,
                    shimmerX + dp(70f), 0f,
                    intArrayOf(Color.TRANSPARENT, electricBlue, iceBlue, electricBlue, Color.TRANSPARENT),
                    null,
                    Shader.TileMode.CLAMP
                )
            }
            canvas.drawRoundRect(
                barLeft, barTop, barLeft + barWidth, barTop + barHeight,
                dp(20f), dp(20f), shimmerPaint
            )
        }

        private fun drawMark(canvas: Canvas, cx: Float, cy: Float, size: Float, scale: Float) {
            val s = size / dp(250f)
            canvas.save()
            canvas.translate(cx, cy)
            canvas.scale(scale * s, scale * s)

            val glow = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = Color.argb(35, 46, 171, 255)
                maskFilter = BlurMaskFilter(dp(22f), BlurMaskFilter.Blur.NORMAL)
            }
            canvas.drawCircle(0f, 0f, dp(88f), glow)

            val ring1 = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = Color.rgb(137, 226, 255)
                style = Paint.Style.STROKE
                strokeWidth = dp(6f)
                strokeCap = Paint.Cap.ROUND
            }
            canvas.drawArc(
                RectF(dp(-108f), dp(-92f), dp(108f), dp(92f)),
                -164f, 238f, false, ring1
            )

            val ring2 = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = Color.argb(230, 29, 116, 255)
                style = Paint.Style.STROKE
                strokeWidth = dp(4f)
                strokeCap = Paint.Cap.ROUND
            }
            canvas.drawArc(
                RectF(dp(-116f), dp(-105f), dp(116f), dp(105f)),
                12f, 212f, false, ring2
            )

            val path = Path().apply {
                moveTo(dp(-77f), dp(73f))
                lineTo(0f, dp(-82f))
                lineTo(dp(77f), dp(73f))
                moveTo(dp(-35f), dp(6f))
                lineTo(dp(35f), dp(6f))
            }
            val aPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                style = Paint.Style.STROKE
                strokeWidth = dp(14f)
                strokeCap = Paint.Cap.SQUARE
                strokeJoin = Paint.Join.MITER
                shader = LinearGradient(
                    0f, dp(-82f), 0f, dp(73f),
                    intArrayOf(Color.rgb(233, 252, 255), electricBlue, Color.rgb(18, 113, 231)),
                    null,
                    Shader.TileMode.CLAMP
                )
            }
            canvas.drawPath(path, aPaint)

            val highlight = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = Color.argb(158, 255, 255, 255)
                style = Paint.Style.STROKE
                strokeWidth = dp(3f)
            }
            canvas.drawPath(path, highlight)

            val play = Path().apply {
                moveTo(dp(72f), dp(-30f))
                lineTo(dp(112f), dp(10f))
                lineTo(dp(72f), dp(50f))
                close()
            }
            canvas.drawPath(play, Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.rgb(191, 243, 255) })

            val white = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.WHITE }
            canvas.drawCircle(dp(74f), dp(-78f), dp(4f), white)
            canvas.drawLine(dp(74f), dp(-94f), dp(74f), dp(-62f), white)
            canvas.drawLine(dp(58f), dp(-78f), dp(90f), dp(-78f), white)
            canvas.drawCircle(dp(108f), dp(-45f), dp(3f), Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = Color.rgb(191, 243, 255)
            })

            canvas.restore()
        }
    }
}
