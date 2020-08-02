package uz.mod.templatex.ui.profile.authorized

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.*
import androidx.core.text.bold
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.R
import uz.mod.templatex.databinding.ProfileAuthorizedFragmentBinding
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.utils.Const
import uz.mod.templatex.utils.extension.lazyFast

class ProfileAuthorizedFragment : ParentFragment() {

    private val navController by lazyFast { findNavController() }
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

    private fun initViews(): Unit = with(binding) {
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
            navController.navigate(R.id.action_profileAuthorizedFragment_to_profileMyOrdersFragment)
        }
        profileMyDataTv.setOnClickListener {
            navController.navigate(R.id.action_profileAuthorizedFragment_to_profileMyDataFragment)
        }
        profileMyAddressesTv.setOnClickListener {
            navController.navigate(R.id.action_profileAuthorizedFragment_to_profileMyAddressesFragment)
        }
        profileFavoritesTv.setOnClickListener {
            navController.navigate(R.id.action_profileAuthorizedFragment_to_profileMyFavoriteFragment)
        }
        profileCallUsTv.setOnClickListener {
            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:${Const.PHONE_NUMBER}")))
        }
        profileCallYouTv.setOnClickListener {
            navController.navigate(R.id.action_profileAuthorizedFragment_to_callMeFragment)
        }
        profileSupportCenterTv.setOnClickListener {
            navController.navigate(R.id.action_profileAuthorizedFragment_to_supportCenterFragment)
        }
        profileAboutModTv.setOnClickListener {
            navController.navigate(R.id.action_profileAuthorizedFragment_to_aboutFragment)
        }
        profileLogoutTv.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
                .setMessage(R.string.logout_text)
                .setPositiveButton(R.string.action_yes) { _, _ ->
                    viewModel?.logout()
                    sharedViewModel.loggedOut()
                    navController.navigate(R.id.action_profileAuthorizedFragment_to_profileFragment)
                }
                .setNegativeButton(R.string.action_no) { _, _ -> }
                .show()

        }
        profileQuestionsBtn.setOnClickListener {
            navController.navigate(R.id.action_profileAuthorizedFragment_to_askQuestionFragment)
        }
    }
    
}