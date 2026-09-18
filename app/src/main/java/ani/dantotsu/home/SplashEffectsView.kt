package ani.dantotsu.home

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.view.View
import android.view.animation.LinearInterpolator
import kotlin.math.cos
import kotlin.math.sin

class SplashEffectsView @JvmOverloads constructor(
    context: Context,
    attrs: android.util.AttributeSet? = null
) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }
    private val starPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }
    private val glowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }
    private val ring = RectF()
    private var rotation = 0f
    private var phase = 0f
    private var animator: ValueAnimator? = null

    init {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        isClickable = false
        isFocusable = false
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        animator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 5200L
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener {
                phase = it.animatedFraction
                rotation = phase * 360f
                invalidate()
            }
            start()
        }
    }

    override fun onDetachedFromWindow() {
        animator?.cancel()
        animator = null
        super.onDetachedFromWindow()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val w = width.toFloat()
        val h = height.toFloat()
        val cx = w / 2f
        val cy = h / 2f - dp(36f)

        // Concentrated blue glow behind the logo, matching the reference.
        glowPaint.color = 0x2238AFFF
        glowPaint.alpha = 115
        glowPaint.setShadowLayer(dp(90f), 0f, 0f, 0x8840BFFF.toInt())
        canvas.drawCircle(cx, cy, dp(88f), glowPaint)
        glowPaint.clearShadowLayer()

        // Thin orbital accents around the logo.
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = dp(1.5f)
        paint.color = 0xB84BAFFF.toInt()
        paint.alpha = 170
        ring.set(cx - dp(150f), cy - dp(72f), cx + dp(150f), cy + dp(72f))
        canvas.drawArc(ring, 202f + rotation * 0.55f, 64f, false, paint)

        paint.color = 0xD06FEFFF.toInt()
        paint.alpha = 220
        ring.set(cx - dp(165f), cy - dp(78f), cx + dp(165f), cy + dp(78f))
        canvas.drawArc(ring, 18f - rotation * 0.35f, 42f, false, paint)

        // A short moving light sweep, echoing the reference's diagonal flare.
        paint.color = 0x8A8EDBFF.toInt()
        paint.alpha = (55 + 75 * (0.5f + 0.5f * sin(phase * Math.PI * 2)).toFloat()).toInt()
        paint.strokeWidth = dp(1f)
        ring.set(cx - dp(210f), cy - dp(105f), cx + dp(210f), cy + dp(105f))
        canvas.drawArc(ring, 122f + rotation * 0.25f, 18f, false, paint)

        // Two prominent reference-style sparkles.
        drawSpark(canvas, cx + dp(120f), cy - dp(118f), dp(11f), 0.55f + 0.45f * sin(phase * Math.PI * 2).toFloat())
        drawSpark(canvas, cx + dp(73f), cy - dp(70f), dp(6f), 0.6f + 0.4f * cos(phase * Math.PI * 2).toFloat())
    }

    private fun drawSpark(canvas: Canvas, x: Float, y: Float, size: Float, alphaFactor: Float) {
        val a = (255f * alphaFactor.coerceIn(0.25f, 1f)).toInt()
        starPaint.color = 0xFFFFFFFF.toInt()
        starPaint.alpha = a
        canvas.drawCircle(x, y, size * 0.18f, starPaint)

        paint.style = Paint.Style.STROKE
        paint.color = 0xFFFFFFFF.toInt()
        paint.alpha = a
        paint.strokeWidth = size * 0.18f
        canvas.drawLine(x, y - size, x, y + size, paint)
        canvas.drawLine(x - size, y, x + size, y, paint)
    }

    private fun dp(v: Float): Float = v * resources.displayMetrics.density
}
