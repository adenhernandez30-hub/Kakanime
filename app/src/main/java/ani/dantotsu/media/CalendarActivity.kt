package ani.dantotsu.media

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import ani.dantotsu.R
import ani.dantotsu.Refresh
import ani.dantotsu.databinding.ActivityListBinding
import ani.dantotsu.media.user.ListViewPagerAdapter
import ani.dantotsu.settings.saving.PrefManager
import ani.dantotsu.settings.saving.PrefName
import ani.dantotsu.statusBarHeight
import ani.dantotsu.themes.ThemeManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CalendarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding
    private val scope = lifecycleScope
    private var selectedTabIdx = 1
    private var showOnlyLibrary = false
    private val model: OtherDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ThemeManager(this).applyTheme()
        binding = ActivityListBinding.inflate(layoutInflater)

        val surface = ContextCompat.getColor(this, R.color.anilab_surface)
        val background = ContextCompat.getColor(this, R.color.anilab_navy)
        val primary = ContextCompat.getColor(this, R.color.anilab_cyan)
        val muted = ContextCompat.getColor(this, R.color.anilab_muted)

        window.statusBarColor = background
        window.navigationBarColor = background
        binding.root.setBackgroundColor(background)
        binding.listAppBar.setBackgroundColor(background)
        binding.settingsContainer.setBackgroundColor(background)
        binding.listTabLayout.setBackgroundColor(surface)
        binding.listTitle.setTextColor(ContextCompat.getColor(this, R.color.anilab_text))
        binding.listTabLayout.setTabTextColors(muted, primary)
        binding.listTabLayout.setSelectedTabIndicatorColor(primary)

        if (!(PrefManager.getVal(PrefName.ImmersiveMode) as Boolean)) {
            binding.root.fitsSystemWindows = true
        } else {
            binding.root.fitsSystemWindows = false
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            hideSystemBarsExtendView()
            binding.settingsContainer.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                topMargin = statusBarHeight
            }
        }
        setContentView(binding.root)

        binding.listTitle.text = "Schedule"
        binding.listTitle.setTextColor(primary)
        binding.listSort.visibility = View.GONE
        binding.random.visibility = View.GONE
        binding.search.visibility = View.VISIBLE
        binding.search.imageTintList = android.content.res.ColorStateList.valueOf(primary)

        binding.listTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                selectedTabIdx = tab?.position ?: 1
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
        })

        binding.listed.setOnClickListener {
            showOnlyLibrary = !showOnlyLibrary
            binding.listed.setImageResource(
                if (showOnlyLibrary) R.drawable.ic_round_collections_bookmark_24
                else R.drawable.ic_round_library_books_24
            )
            scope.launch { model.loadCalendar(showOnlyLibrary) }
        }

        model.getCalendar().observe(this) {
            if (it != null) {
                binding.listProgressBar.visibility = View.GONE
                binding.listViewPager.adapter = ListViewPagerAdapter(it.size, true, this)
                val keys = it.keys.toList()
                val values = it.values.toList()
                TabLayoutMediator(binding.listTabLayout, binding.listViewPager) { tab, position ->
                    tab.text = "${keys[position]} (${values[position].size})"
                }.attach()
                binding.listViewPager.setCurrentItem(selectedTabIdx.coerceAtMost(it.size - 1), false)
            }
        }

        val live = Refresh.activity.getOrPut(this.hashCode()) { MutableLiveData(true) }
        live.observe(this) {
            if (it) {
                scope.launch {
                    withContext(Dispatchers.IO) { model.loadCalendar(showOnlyLibrary) }
                    live.postValue(false)
                }
            }
        }
    }
}
