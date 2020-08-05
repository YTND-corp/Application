package uz.mod.templatex.ui.signUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import uz.mod.templatex.utils.extension.getNavigationResult
import uz.mod.templatex.utils.extension.lazyFast


class SignUpFragment : ParentFragment() {

    private val navController by lazyFast { findNavController() }
    val viewModel: SignUpViewModel by viewModel()
    private val binding by lazy { SignUpFragmentBinding.inflate(layoutInflater) }

    companion object {
        fun newInstance() = SignUpFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        viewModel.response.observe(viewLifecycleOwner, Observer {
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
                                viewModel.phone.value!!,
                                false
                            )
                        )
                    }
                }
            }
        })

    }

    private fun initViews(): Unit = with(binding) {
        viewModel = this@SignUpFragment.viewModel
        executePendingBindings()

        phone.addTextChangedListener(MaskWatcher.phoneWatcher())

        phone.setOnFocusChangeListener { _, hasFocus ->

            if (hasFocus) {
                if (phone.text?.length ?: 0 < 5 && phone.text?.equals(Const.PHONE_CODE_DEFAULT) == false) {
                    phone.setText(Const.PHONE_CODE_DEFAULT)
                }
            } else {
                if (phone.text?.length ?: 0 <= 5) {
                    phone.setText("")
                }
            }
        }
    }


    private fun processError(error: ApiError?) {
        when (error?.code) {
            Const.API_NO_CONNECTION_STATUS_CODE -> navigateAndObserveResult(R.id.noInternetFragment)
            Const.API_SERVER_FAIL_STATUS_CODE -> navigateAndObserveResult(R.id.serverErrorDialogFragment)
            Const.API_NEW_VERSION_AVAILABLE_STATUS_CODE -> navController.navigate(R.id.newVersionAvailableFragmentDialog)
            else -> showError(error)
        }
    }

    private fun navigateAndObserveResult(@IdRes destinationID: Int) {
        navController.getNavigationResult<Event<Boolean>>()?.observe(viewLifecycleOwner, Observer {
            if (it.getContentIfNotHandled() == true) viewModel.signUp()
        })
        navController.navigate(destinationID)
    }
}
