package ani.dantotsu

import android.animation.AnimatorSet
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import ani.dantotsu.databinding.SplashScreenBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: SplashScreenBinding
    private var launchedMain = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        runSplashTimeline()
    }

    private fun runSplashTimeline() {
        val density = resources.displayMetrics.density
        val sweepDistance = 220f * density
        val wordmarkLift = 12f * density

        binding.splashRoot.alpha = 0f
        binding.splashImage.alpha = 0f
        binding.splashImage.scaleX = 0.92f
        binding.splashImage.scaleY = 0.92f
        binding.splashWordmark.alpha = 0f
        binding.splashWordmark.translationY = wordmarkLift
        binding.splashGlow.alpha = 0.24f
        binding.splashRing.alpha = 0f
        binding.splashRing.rotation = -12f
        binding.splashParticles.alpha = 0f
        binding.splashSweep.alpha = 0f
        binding.splashSweep.translationX = -sweepDistance
        binding.splashProgress.scaleX = 0f
        (binding.splashImage.drawable as? Animatable)?.start()

        val bgIn = ObjectAnimator.ofFloat(binding.splashRoot, "alpha", 0f, 1f).apply {
            duration = 200L
            interpolator = DecelerateInterpolator()
        }
        val logoFade = ObjectAnimator.ofFloat(binding.splashImage, "alpha", 0f, 1f).apply {
            startDelay = 200L
            duration = 300L
            interpolator = DecelerateInterpolator()
        }
        val logoScaleX = ObjectAnimator.ofFloat(binding.splashImage, "scaleX", 0.92f, 1f).apply {
            startDelay = 200L
            duration = 300L
            interpolator = DecelerateInterpolator()
        }
        val logoScaleY = ObjectAnimator.ofFloat(binding.splashImage, "scaleY", 0.92f, 1f).apply {
            startDelay = 200L
            duration = 300L
            interpolator = DecelerateInterpolator()
        }
        val ringIn = ObjectAnimator.ofFloat(binding.splashRing, "alpha", 0f, 0.92f).apply {
            startDelay = 500L
            duration = 400L
            interpolator = DecelerateInterpolator()
        }
        val ringRotate = ObjectAnimator.ofFloat(binding.splashRing, "rotation", -12f, 8f).apply {
            startDelay = 500L
            duration = 400L
            interpolator = AccelerateDecelerateInterpolator()
        }
        val glowIn = ObjectAnimator.ofFloat(binding.splashGlow, "alpha", 0.24f, 0.62f).apply {
            startDelay = 500L
            duration = 400L
        }
        val particlesIn = ObjectAnimator.ofFloat(binding.splashParticles, "alpha", 0f, 0.7f).apply {
            startDelay = 560L
            duration = 320L
        }
        val wordmarkIn = ObjectAnimator.ofFloat(binding.splashWordmark, "alpha", 0f, 1f).apply {
            startDelay = 900L
            duration = 300L
        }
        val wordmarkSlide = ObjectAnimator.ofFloat(binding.splashWordmark, "translationY", wordmarkLift, 0f).apply {
            startDelay = 900L
            duration = 300L
            interpolator = DecelerateInterpolator()
        }
        val sweepIn = ObjectAnimator.ofFloat(binding.splashSweep, "alpha", 0f, 0.85f).apply {
            startDelay = 1200L
            duration = 120L
        }
        val sweepMove = ObjectAnimator.ofFloat(binding.splashSweep, "translationX", -sweepDistance, sweepDistance).apply {
            startDelay = 1200L
            duration = 380L
            interpolator = AccelerateDecelerateInterpolator()
        }
        val progressIn = ObjectAnimator.ofFloat(binding.splashProgress, "scaleX", 0f, 1f).apply {
            startDelay = 1600L
            duration = 380L
            interpolator = DecelerateInterpolator()
        }

        AnimatorSet().apply {
            playTogether(
                bgIn,
                logoFade,
                logoScaleX,
                logoScaleY,
                ringIn,
                ringRotate,
                glowIn,
                particlesIn,
                wordmarkIn,
                wordmarkSlide,
                sweepIn,
                sweepMove,
                progressIn
            )
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: android.animation.Animator) {
                    launchMain()
                }
            })
            start()
        }
    }

    private fun launchMain() {
        if (launchedMain || isFinishing || isDestroyed) return
        launchedMain = true
        startActivity(
            Intent(this, MainActivity::class.java).putExtra(
                MainActivity.EXTRA_SKIP_STARTUP_SPLASH,
                true
            )
        )
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }
}
