package uz.mod.templatex.ui.paymentProvider

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
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

    private val args: PaymentDetailsFragmentArgs by navArgs()
    private val paymentViewModel: PaymentDetailsViewModel by viewModel()
    private lateinit var binding: FragmentPaymentDetailsBinding
    private val navController by lazyFast { findNavController() }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPaymentDetailsBinding.inflate(inflater, container, false)
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

    private fun initViews(): Unit = with(binding) {
        viewModel = paymentViewModel
        executePendingBindings()
        sharedViewModel.title.value = args.paymentData.provider?.name

        expiry.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.isNotEmpty() && s.length % 3 == 0) {
                    val c: Char = s[s.length - 1]
                    if ('/' == c) {
                        s.delete(s.length - 1, s.length)
                    }
                }
                if (s.isNotEmpty() && s.length % 3 == 0) {
                    val c: Char = s[s.length - 1]
                    if (Character.isDigit(c) && TextUtils.split(
                            s.toString(),
                            "/"
                        ).size <= 2
                    ) {
                        s.insert(s.length - 1, "/")
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        continueButton.setOnClickListener {
            paymentViewModel.pay()
        }
    }


    private fun observePayState() {
        paymentViewModel.response.observe(viewLifecycleOwner, Observer { result ->
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
        paymentViewModel.store.observe(viewLifecycleOwner, Observer { result ->
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
                        args.details,
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