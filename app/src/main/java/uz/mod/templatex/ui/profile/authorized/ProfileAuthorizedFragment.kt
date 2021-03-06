package uz.mod.templatex.ui.profile.authorized

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.*
import androidx.core.text.bold
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.R
import uz.mod.templatex.databinding.ProfileAuthorizedFragmentBinding
import uz.mod.templatex.ui.parent.ParentFragment

class ProfileAuthorizedFragment : ParentFragment() {

    val viewModel: ProfileAuthorizedViewModel by viewModel()

    private val binding by lazy { ProfileAuthorizedFragmentBinding.inflate(layoutInflater) }

    companion object {
        fun newInstance() = ProfileAuthorizedFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.profile_authorized_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settingsAuthorizedFragment -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this@ProfileAuthorizedFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@ProfileAuthorizedFragment.viewModel
            executePendingBindings()

            val profileName = this@ProfileAuthorizedFragment.viewModel.getUser().name + "!"
            val profileNameTitleText = SpannableStringBuilder()
                .append(getString(R.string.profile_authorized_name_title_first_part))
                .bold { append(profileName) }

            profileNameTitleTv.text = profileNameTitleText

            profileHeaderContainer.setOnClickListener {
                // TODO
            }
            profileMyOrdersTv.setOnClickListener {
                findNavController().navigate(R.id.action_profileAuthorizedFragment_to_profileMyOrdersFragment)
            }
            profileMyDataTv.setOnClickListener {
                // TODO
            }
            profileMyAddressesTv.setOnClickListener {
                // TODO
            }
            profileFavoritesTv.setOnClickListener {
                // TODO
            }
            profileCallUsTv.setOnClickListener {
                // TODO
            }
            profileCallYouTv.setOnClickListener {
                // TODO
            }
            profileSupportCenterTv.setOnClickListener {
                // TODO
            }
            profileAboutModTv.setOnClickListener {
                // TODO
            }
            profileLogoutTv.setOnClickListener {
                // TODO
            }
            profileQuestionsBtn.setOnClickListener {
                // TODO
            }
        }
    }
}