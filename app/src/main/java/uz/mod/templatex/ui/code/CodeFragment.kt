package uz.mod.templatex.ui.code

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.R
import uz.mod.templatex.databinding.CodeFragmentBinding
import uz.mod.templatex.model.remote.network.ApiError
import uz.mod.templatex.model.remote.network.Status
import uz.mod.templatex.ui.address.AddressFragmentDirections
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.utils.Const
import uz.mod.templatex.utils.Event
import uz.mod.templatex.utils.extension.getNavigationResult
import uz.mod.templatex.utils.extension.lazyFast

class CodeFragment : ParentFragment() {

    private val navController by lazyFast { findNavController() }
    val viewModel: CodeViewModel by viewModel()
    val args: CodeFragmentArgs by navArgs()

    private val binding by lazy { CodeFragmentBinding.inflate(layoutInflater) }

    companion object {
        fun newInstance() = CodeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setArguments(args)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this@CodeFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        if (args.isCheckout) {
            viewModel.checkoutConfirmResponse.observe(viewLifecycleOwner, Observer { result ->
                when (result.status) {
                    Status.LOADING -> showLoading()
                    Status.ERROR -> {
                        hideLoading()
                        processError(result.error)
                    }
                    Status.SUCCESS -> {
                        hideLoading()
                        binding.code.text = null
                        sharedViewModel.loggedIn(result.data?.user)
                        if (args.isCheckout) {
                            navController.navigate(
                                AddressFragmentDirections.actionGlobalAddressFragment(
                                    args.cartResponse,
                                    result.data,
                                    args.phone
                                )
                            )
                        } else {
                            navController.popBackStack(R.id.profileFragment, true)
                        }
                    }
                }
            })
        } else {
            viewModel.authConfirmResponse.observe(viewLifecycleOwner, Observer { result ->
                when (result.status) {
                    Status.LOADING -> showLoading()
                    Status.ERROR -> {
                        hideLoading()
                        processError(result.error)
                    }
                    Status.SUCCESS -> {
                        hideLoading()
                        binding.code.text = null
                        sharedViewModel.loggedIn(result.data)
                        navController.navigate(R.id.action_codeFragment_to_profileFragment)
                    }
                }
            })
        }
    }

    private fun initViews(): Unit = with(binding) {
        viewModel = this@CodeFragment.viewModel
        executePendingBindings()

        viewModel?.code?.observe(viewLifecycleOwner, Observer {
            if (it.length != 4) return@Observer
            if (args.isCheckout) viewModel?.checkoutConfirm()
            else viewModel?.authConfirm()
        })

        resendButton.setOnClickListener {}

        change.setOnClickListener {
            navController.popBackStack()
        }
    }

    //TODO("PLEASE CHECK FOLLOWING LINES IF THE LOGIC IS NOT BROKEN")
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
            if (it.getContentIfNotHandled() == true) retryLastRequest()
        })
        navController.navigate(destinationID)
    }

    private fun retryLastRequest() {
        if (args.isCheckout) viewModel.checkoutConfirm()
        else viewModel.authConfirm()
    }
}
