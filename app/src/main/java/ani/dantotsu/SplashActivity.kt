package ani.dantotsu

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import ani.dantotsu.databinding.SplashScreenBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = SplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (binding.splashImage.drawable as? android.graphics.drawable.Animatable)?.start()

        lifecycleScope.launch {
            delay(900)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }
    }
}
