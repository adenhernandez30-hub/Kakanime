package ani.dantotsu.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ani.dantotsu.databinding.FragmentSocialBinding
import ani.dantotsu.navBarHeight

class SocialFragment : Fragment() {
    private var _binding: FragmentSocialBinding? = null
    private val binding get() = _binding!!

    private val activeRoomsAdapter = SocialListAdapter()
    private val myRoomsAdapter = SocialListAdapter()
    private val messagesAdapter = SocialListAdapter()
    private val notificationsAdapter = SocialListAdapter()
    private val usersAdapter = SocialListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSocialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.socialContainer.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            bottomMargin = navBarHeight
        }

        binding.socialActiveRoomsRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.socialMyRoomsRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.socialMessagesRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.socialNotificationsRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.socialUsersRecycler.layoutManager = LinearLayoutManager(requireContext())

        binding.socialActiveRoomsRecycler.adapter = activeRoomsAdapter
        binding.socialMyRoomsRecycler.adapter = myRoomsAdapter
        binding.socialMessagesRecycler.adapter = messagesAdapter
        binding.socialNotificationsRecycler.adapter = notificationsAdapter
        binding.socialUsersRecycler.adapter = usersAdapter

        activeRoomsAdapter.submit(
            listOf(
                SocialItemUi("Watch Together", "Your Name Movie Night • 13 viewers • Live"),
                SocialItemUi("Active Room", "Frieren EP Marathon • 8 viewers • Lobby")
            )
        )
        myRoomsAdapter.submit(
            listOf(
                SocialItemUi("Create Room", "Room name + privacy + invite friends"),
                SocialItemUi("Join Room", "Use room code or direct invite")
            )
        )
        messagesAdapter.submit(
            listOf(
                SocialItemUi("Messages / DM", "Rimuru: Episode sync starts in 5 minutes"),
                SocialItemUi("Chat Detail", "Ainz: Queue episode 3 after this one")
            )
        )
        notificationsAdapter.submit(
            listOf(
                SocialItemUi("Notifications", "2 friend requests • 1 room invite"),
                SocialItemUi("Room Lobby", "Prepare playback quality and subtitle preferences")
            )
        )
        usersAdapter.submit(
            listOf(
                SocialItemUi("Search Users", "Find friends by username and shared anime"),
                SocialItemUi("Other User Profile", "View badges, favorites, and rooms")
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
