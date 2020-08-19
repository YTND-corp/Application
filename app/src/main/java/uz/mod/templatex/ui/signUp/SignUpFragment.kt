package uz.mod.templatex.ui.signUp

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.R
import uz.mod.templatex.databinding.SignUpFragmentBinding
import uz.mod.templatex.model.remote.network.ApiError
import uz.mod.templatex.model.remote.network.Status
import uz.mod.templatex.ui.code.CodeFragmentDirections
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.utils.Const
import uz.mod.templatex.utils.Event
import uz.mod.templatex.utils.MaskWatcher
import uz.mod.templatex.utils.PhoneFieldFocusChangeListener
import uz.mod.templatex.utils.extension.getNavigationResult
import uz.mod.templatex.utils.extension.lazyFast


class SignUpFragment : ParentFragment() {

    private val navController by lazyFast { findNavController() }
    private val signUpViewModel: SignUpViewModel by viewModel()
    private val textWatcher by lazyFast { MaskWatcher.phoneWatcher() }
    private val focusChangeLister by lazyFast { PhoneFieldFocusChangeListener() }
    private val binding: SignUpFragmentBinding
        get() = childBinding as SignUpFragmentBinding

    companion object {
        fun newInstance() = SignUpFragment()
    }

    override fun getLayoutID(): Int? = R.layout.sign_up_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        signUpViewModel.response.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    Status.LOADING -> showLoading()
                    Status.ERROR -> {
                        hideLoading()
                        processError(result.error)
                    }
                    Status.SUCCESS -> {
                        hideLoading()
                        navController.navigate(
                            CodeFragmentDirections.actionGlobalCodeFragment(
                                null,
                                signUpViewModel.phone.value!!,
                                false
                            )
                        )
                    }
                }
            }
        })

    }

    private fun initViews(): Unit = with(binding) {
        viewModel = signUpViewModel
        executePendingBindings()

        continueButton.setOnClickListener {
            hideKeyboard()
            viewModel?.signUp()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.phone.addTextChangedListener(textWatcher)
        binding.phone.onFocusChangeListener = focusChangeLister
    }

    override fun onStop() {
        super.onStop()
        binding.phone.removeTextChangedListener(textWatcher)
    }


    private fun processError(error: ApiError?) = when (error?.code) {
        Const.API_NO_CONNECTION_STATUS_CODE -> navigateAndObserveResult(R.id.noInternetFragment)
        Const.API_SERVER_FAIL_STATUS_CODE -> navigateAndObserveResult(R.id.serverErrorDialogFragment)
        Const.API_NEW_VERSION_AVAILABLE_STATUS_CODE -> navController.navigate(R.id.newVersionAvailableFragmentDialog)
        else -> showError(error)
    }


    private fun navigateAndObserveResult(@IdRes destinationID: Int) {
        navController.getNavigationResult<Event<Boolean>>()?.observe(viewLifecycleOwner, Observer {
            if (it.getContentIfNotHandled() == true) signUpViewModel.signUp()
        })
        navController.navigate(destinationID)
    }
}
