package ani.dantotsu.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ani.dantotsu.connections.anilist.Anilist
import ani.dantotsu.databinding.FragmentAccountBinding
import ani.dantotsu.loadImage
import ani.dantotsu.navBarHeight

class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private val menuAdapter = AccountMenuAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.accountContainer.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            bottomMargin = navBarHeight
        }

        binding.accountName.text = Anilist.username.ifBlank { "AniLab User" }
        binding.accountBio.text = "Watch anime, together."
        binding.accountEpisodesValue.text = (Anilist.episodesWatched).toString()
        binding.accountAnimeValue.text = "${Anilist.episodesWatched / 12}"
        binding.accountFavoritesValue.text = "24"
        binding.accountDiamondsValue.text = "1,320"
        binding.accountAvatar.loadImage(Anilist.avatar)
        binding.accountBanner.loadImage(Anilist.bg.ifBlank { Anilist.avatar })

        binding.accountEditProfile.setOnClickListener {
            EditProfileBottomSheet().show(parentFragmentManager, "edit_profile")
        }

        binding.accountMenuRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.accountMenuRecycler.adapter = menuAdapter
        menuAdapter.submit(
            listOf(
                AccountMenuUi("Profile", "Identity, bio, and linked profiles"),
                AccountMenuUi("Premium & Diamonds", "Manage premium status and diamonds"),
                AccountMenuUi("Appearance", "Accent color and theme preferences"),
                AccountMenuUi("Notifications", "Push, social, and room alerts"),
                AccountMenuUi("Settings", "General app preferences"),
                AccountMenuUi("About", "AniLab app info and acknowledgements")
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
