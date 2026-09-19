package ani.dantotsu.settings

import android.content.ComponentName
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import ani.dantotsu.R
import ani.dantotsu.databinding.ActivityAppIconBinding
import ani.dantotsu.initActivity
import ani.dantotsu.navBarHeight
import ani.dantotsu.statusBarHeight

/** Uses activity-alias components so the launcher icon can change without rebuilding the APK. */
class AppIconActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppIconBinding

    private val aliases = listOf(
        R.id.iconBlue to "ani.dantotsu.launcher.Blue",
        R.id.iconDark to "ani.dantotsu.launcher.Dark",
        R.id.iconMono to "ani.dantotsu.launcher.Mono"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivity(this)

        binding = ActivityAppIconBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.root.setPadding(
            binding.root.paddingLeft,
            statusBarHeight,
            binding.root.paddingRight,
            navBarHeight
        )

        binding.appIconBack.setOnClickListener { finish() }

        binding.iconBlue.setOnClickListener { selectIcon("ani.dantotsu.launcher.Blue") }
        binding.iconDark.setOnClickListener { selectIcon("ani.dantotsu.launcher.Dark") }
        binding.iconMono.setOnClickListener { selectIcon("ani.dantotsu.launcher.Mono") }

        refreshSelection()
    }

    override fun onResume() {
        super.onResume()
        if (::binding.isInitialized) refreshSelection()
    }

    private fun refreshSelection() {
        val active = aliases.firstOrNull { (_, className) ->
            packageManager.getComponentEnabledSetting(ComponentName(this, className)) ==
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED
        }?.first ?: R.id.iconBlue

        binding.iconBlueCheck.isVisible = active == R.id.iconBlue
        binding.iconDarkCheck.isVisible = active == R.id.iconDark
        binding.iconMonoCheck.isVisible = active == R.id.iconMono
    }

    private fun selectIcon(className: String) {
        aliases.forEach { (_, alias) ->
            packageManager.setComponentEnabledSetting(
                ComponentName(this, alias),
                if (alias == className) {
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                } else {
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                },
                PackageManager.DONT_KILL_APP
            )
        }
        refreshSelection()
    }
}
