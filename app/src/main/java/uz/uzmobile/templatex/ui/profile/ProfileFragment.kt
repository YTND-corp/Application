package uz.uzmobile.templatex.ui.profile

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.uzmobile.templatex.R
import uz.uzmobile.templatex.databinding.ProfileFragmentBinding
import uz.uzmobile.templatex.extension.drawable
import uz.uzmobile.templatex.model.local.entity.ProfileItem


class ProfileFragment : Fragment() {

    val viewModel: ProfileViewModel by viewModel()

    private val binding by lazy { ProfileFragmentBinding.inflate(layoutInflater) }

    private lateinit var adapter: ProfileAdapter

    companion object {
        fun newInstance() = ProfileFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.profile_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settingsFragment -> true;
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this@ProfileFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@ProfileFragment.viewModel
            executePendingBindings()

            adapter = ProfileAdapter(getItems())
            profileRecyclerView.hasFixedSize()
            profileRecyclerView.adapter = adapter

            signInButton.setOnClickListener {
                findNavController().navigate(R.id.action_global_sign_in_graph)
            }

            signUpButton.setOnClickListener {
                findNavController().navigate(R.id.action_global_sign_up_graph)
            }
        }
    }

    private fun getItems(): ArrayList<ProfileItem> {
        val list = ArrayList<ProfileItem>()
        list.add(ProfileItem(0, context?.drawable(R.drawable.ic_country), context?.getString(R.string.profile_country)))
        list.add(ProfileItem(1, context?.drawable(R.drawable.ic_check_order_status), context?.getString(R.string.profile_check_status_order)))
        list.add(ProfileItem(2, context?.drawable(R.drawable.ic_message), context?.getString(R.string.profile_ask_question)))
        list.add(ProfileItem(3, context?.drawable(R.drawable.ic_call), context?.getString(R.string.profile_call_us)))
        list.add(ProfileItem(4, context?.drawable(R.drawable.ic_recive_call), context?.getString(R.string.profile_call_you)))
        list.add(ProfileItem(5, context?.drawable(R.drawable.ic_support), context?.getString(R.string.profile_call_support_center)))
        list.add(ProfileItem(6, context?.drawable(R.drawable.ic_about_app), context?.getString(R.string.profile_about_app)))
        return list
    }
}