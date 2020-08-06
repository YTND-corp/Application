package uz.mod.templatex.ui.paymentProvider

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
import uz.mod.templatex.databinding.FragmentPaymentDetailsBinding
import uz.mod.templatex.model.remote.network.ApiError
import uz.mod.templatex.model.remote.network.Status
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.utils.Const
import uz.mod.templatex.utils.Event
import uz.mod.templatex.utils.extension.getNavigationResult
import uz.mod.templatex.utils.extension.lazyFast

class PaymentDetailsFragment : ParentFragment() {

    private val args : PaymentDetailsFragmentArgs by navArgs()
    private val paymentViewModel: PaymentDetailsViewModel by viewModel()
    private val binding by lazy { FragmentPaymentDetailsBinding.inflate(layoutInflater) }
    private val navController by lazyFast { findNavController() }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args.paymentData.phone = sharedViewModel.prefs.phone
        paymentViewModel.setArguments(args)
        initViews()
        observePayState()
        observeStoringState()
    }

    private fun initViews() : Unit = with(binding) {
        viewModel = paymentViewModel
        executePendingBindings()
        sharedViewModel.title.value = args.paymentData.provider?.name

        continueButton.setOnClickListener {
            paymentViewModel.pay()
        }
    }


    private fun observePayState() {
        paymentViewModel.response.observe(viewLifecycleOwner, Observer {result->
            when (result.status) {
                Status.LOADING -> showLoading()
                Status.ERROR -> {
                    hideLoading()
                    processError(result.error)
                }
                Status.SUCCESS -> paymentViewModel.store()
            }
        })
    }

    private fun observeStoringState() {
        paymentViewModel.store.observe(viewLifecycleOwner, Observer { result->
            when (result.status) {
                Status.LOADING -> showLoading()
                Status.ERROR -> {
                    hideLoading()
                    processError(result.error)
                }
                Status.SUCCESS -> {
                    hideLoading()
                    PaymentDetailsFragmentDirections.actionPaymentDetailsFragmentToCheckoutFinalFragment(
                        args.cartResponse,
                        args.response,
                        result.data
                    ).run { navController.navigate(this) }
                }
            }
        })
    }

    private fun processError(error: ApiError?) = when (error?.code) {
        Const.API_NO_CONNECTION_STATUS_CODE -> navigateAndObserveResult(R.id.noInternetFragment)
        Const.API_SERVER_FAIL_STATUS_CODE -> navigateAndObserveResult(R.id.serverErrorDialogFragment)
        Const.API_NEW_VERSION_AVAILABLE_STATUS_CODE -> navController.navigate(R.id.newVersionAvailableFragmentDialog)
        else -> showError(error)
    }

    private fun navigateAndObserveResult(@IdRes destinationID: Int) {
        navController.getNavigationResult<Event<Boolean>>()?.observe(viewLifecycleOwner, Observer {
            if (it.getContentIfNotHandled() == true) paymentViewModel.retryLastRequest()
        })
        navController.navigate(destinationID)
    }
}