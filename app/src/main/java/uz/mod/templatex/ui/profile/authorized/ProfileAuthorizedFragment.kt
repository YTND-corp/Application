package uz.mod.templatex.ui.profile.authorized

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.R
import uz.mod.templatex.databinding.ProfileAuthorizedFragmentBinding
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.utils.Const
import uz.mod.templatex.utils.extension.lazyFast
import java.lang.IllegalArgumentException

class ProfileAuthorizedFragment : ParentFragment() {

    private val navController by lazyFast { findNavController() }
    private val profileAuthorizedViewModel: ProfileAuthorizedViewModel by viewModel()

    private lateinit var binding: ProfileAuthorizedFragmentBinding

    companion object {
        fun newInstance() = ProfileAuthorizedFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProfileAuthorizedFragmentBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            initViews()
        }catch (e: IllegalArgumentException) {

        }
    }

    private fun initViews(): Unit = with(binding) {
        viewModel = profileAuthorizedViewModel
        executePendingBindings()

        val profileName = profileAuthorizedViewModel.getUserName() + "!"

        profileNameTitleTv.text = getString(R.string.profile_authorized_name_title_first_part, profileName)

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
                    profileAuthorizedViewModel.logout()
                    sharedViewModel.loggedOut()
                    navController.navigate(R.id.action_profileAuthorizedFragment_to_profileFragment)
                }
                .setNegativeButton(R.string.action_no) { _, _ -> }
                .show()
        }
    }
}