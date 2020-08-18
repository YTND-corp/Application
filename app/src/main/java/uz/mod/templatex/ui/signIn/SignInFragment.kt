package uz.mod.templatex.ui.signIn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.code_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
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
    private lateinit var binding: SignInFragmentBinding
    private val textWatcher by lazyFast { MaskWatcher.phoneWatcher() }
    private val focusChangeListener by lazyFast { PhoneFieldFocusChangeListener() }

    companion object {
        fun newInstance() = SignInFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SignInFragmentBinding.inflate(inflater, container, false)
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
        viewModel = this@SignInFragment.viewModel
        executePendingBindings()
    }

    override fun onResume() {
        super.onResume()
        phone.addTextChangedListener(textWatcher)
        phone.onFocusChangeListener = focusChangeListener
    }

    override fun onStop() {
        super.onStop()
        phone.removeTextChangedListener(textWatcher)
    }

    private fun processError(error: ApiError?) = when (error?.code) {
        Const.API_NO_CONNECTION_STATUS_CODE -> navigateAndObserveResult(R.id.noInternetFragment)
        Const.API_SERVER_FAIL_STATUS_CODE -> navigateAndObserveResult(R.id.serverErrorDialogFragment)
        Const.API_NEW_VERSION_AVAILABLE_STATUS_CODE -> navController.navigate(R.id.newVersionAvailableFragmentDialog)
        else -> showError(error)
    }


    private fun navigateAndObserveResult(@IdRes destinationID: Int) {
        navController.getNavigationResult<Event<Boolean>>()?.observe(viewLifecycleOwner, Observer {
            if (it.getContentIfNotHandled() == true) viewModel.signIn()
        })
        navController.navigate(destinationID)
    }

}
