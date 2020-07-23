package uz.mod.templatex.ui.profile.guest

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.R
import uz.mod.templatex.databinding.ProfileGuestFragmentBinding
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.utils.Const


class ProfileGuestFragment : ParentFragment() {

    val viewModel: ProfileGuestViewModel by viewModel()

    private val binding by lazy { ProfileGuestFragmentBinding.inflate(layoutInflater) }

    companion object {
        fun newInstance() = ProfileGuestFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this@ProfileGuestFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        sharedViewModel.isAuthenticated.observe(viewLifecycleOwner, Observer { isAuthorized ->
            if (isAuthorized) {
                //binding.unauthorized
                findNavController().navigate(R.id.action_profileFragment_to_profileAuthorizedFragment)
            } else {

            }
            binding.name.text = viewModel.getUserName()
            binding.phone.text = viewModel.getUserPhone()
        })
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@ProfileGuestFragment.viewModel
            sharedViewModel = this@ProfileGuestFragment.sharedViewModel
            executePendingBindings()

            signIn.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_sign_in_graph)
            }

            signUp.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_sign_up_graph)
            }

            country.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_countryFragment)
            }

            checkOrderStatus.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_checkOrderStatusFragment)
            }

            askQuestion.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_askQuestionFragment)
            }

            callUs.setOnClickListener {
                startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:${Const.PHONE_NUMBER}")))
            }

            callMe.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_callMeFragment)
            }

            supportCenter.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_supportFragment)
            }

            about.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_aboutFragment)
            }
        }
    }
}