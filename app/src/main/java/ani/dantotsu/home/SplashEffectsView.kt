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
    private var reveal = 0f
    private var animator: ValueAnimator? = null

    init {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        isClickable = false
        isFocusable = false
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        animator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 4200L
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener {
                rotation = it.animatedFraction * 360f
                reveal = it.animatedFraction
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
        val cy = h / 2f - dp(18f)
        val rx = w * 0.48f
        val ry = h * 0.37f

        glowPaint.color = 0x1838AFFF
        glowPaint.alpha = 100
        glowPaint.setShadowLayer(dp(75f), 0f, 0f, 0x6640BFFF)
        canvas.drawCircle(cx, cy, dp(70f), glowPaint)
        glowPaint.clearShadowLayer()

        paint.strokeWidth = dp(1.1f)
        paint.color = 0x653B8CFF.toInt()
        paint.alpha = 150
        ring.set(cx - rx, cy - ry, cx + rx, cy + ry)
        canvas.drawArc(ring, 205f + rotation, 76f, false, paint)

        paint.color = 0x8040BFFF.toInt()
        paint.alpha = 105
        ring.set(cx - rx * 0.82f, cy - ry * 0.72f, cx + rx * 0.82f, cy + ry * 0.72f)
        canvas.drawArc(ring, 25f - rotation * 0.55f, 55f, false, paint)

        paint.color = 0xB84AC7FF.toInt()
        paint.strokeWidth = dp(0.8f)
        paint.alpha = 90
        ring.set(cx - rx * 1.08f, cy - ry * 0.82f, cx + rx * 1.08f, cy + ry * 0.82f)
        canvas.drawArc(ring, 310f + rotation * 0.35f, 34f, false, paint)

        drawSpark(canvas, cx + w * 0.23f, cy - h * 0.17f, dp(5f), 0.35f + 0.65f * sin(reveal * Math.PI * 2).toFloat())
        drawSpark(canvas, cx - w * 0.25f, cy + h * 0.16f, dp(3f), 0.45f + 0.55f * cos(reveal * Math.PI * 2).toFloat())
        drawSpark(canvas, cx + w * 0.31f, cy + h * 0.05f, dp(2.2f), 0.25f + 0.75f * sin(reveal * Math.PI * 2 + 1.4).toFloat())
    }

    private fun drawSpark(canvas: Canvas, x: Float, y: Float, size: Float, alphaFactor: Float) {
        val a = (255f * alphaFactor.coerceIn(0.08f, 1f)).toInt()
        starPaint.color = 0xFFFFFFFF.toInt()
        starPaint.alpha = a
        canvas.drawCircle(x, y, size * 0.24f, starPaint)

        paint.color = 0xCC6EDBFF.toInt()
        paint.alpha = a
        paint.strokeWidth = size * 0.22f
        canvas.drawLine(x - size, y, x + size, y, paint)
        canvas.drawLine(x, y - size, x, y + size, paint)
    }

    private fun dp(v: Float): Float = v * resources.displayMetrics.density
}
