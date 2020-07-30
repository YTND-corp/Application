package uz.mod.templatex.ui.signIn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import uz.mod.templatex.R
import uz.mod.templatex.databinding.SignInFragmentBinding
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

class SignInFragment : ParentFragment() {

    private val navController by lazyFast { findNavController() }
    val viewModel: SignInViewModel by viewModel()
    private val binding by lazy { SignInFragmentBinding.inflate(layoutInflater) }

    companion object {
        fun newInstance() = SignInFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this@SignInFragment
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
                        Timber.e(result.data.toString())
                        navController.navigate(
                            CodeFragmentDirections.actionGlobalCodeFragment(
                                viewModel.phone.value!!,
                                false
                            )
                        )
                    }
                }
            }
        })
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@SignInFragment.viewModel
            executePendingBindings()

            phone.addTextChangedListener(MaskWatcher.phoneWatcher())
            phone.onFocusChangeListener = PhoneFieldFocusChangeListener()
        }
    }

    private fun processError(error: ApiError?) {
        when (error?.code) {
            Const.API_NO_CONNECTION_STATUS_CODE -> {
                navController.getNavigationResult<Event<Boolean>>()?.observe(viewLifecycleOwner, Observer {
                    if (it.getContentIfNotHandled() == true) viewModel.signIn()
                })
                navController.navigate(R.id.noInternetFragment)
            }
            Const.API_SERVER_FAIL_STATUS_CODE -> {
                navController.getNavigationResult<Event<Boolean>>()?.observe(viewLifecycleOwner, Observer {
                    if (it.getContentIfNotHandled() == true) viewModel.signIn()
                })
                navController.navigate(R.id.serverErrorDialogFragment)
            }
            else -> showError(error)
        }
    }
}
