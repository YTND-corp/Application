package uz.mod.templatex.ui.payment

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
import uz.mod.templatex.databinding.PaymentFragmentBinding
import uz.mod.templatex.model.inApp.PaymentData
import uz.mod.templatex.model.remote.network.ApiError
import uz.mod.templatex.model.remote.network.Status
import uz.mod.templatex.model.remote.response.PaymentMethod
import uz.mod.templatex.ui.custom.LineDividerItemDecoration
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.utils.Const
import uz.mod.templatex.utils.Event
import uz.mod.templatex.utils.extension.getNavigationResult
import uz.mod.templatex.utils.extension.lazyFast

class PaymentFragment : ParentFragment() {

    private val navController by lazyFast { findNavController() }
    private val paymentViewModel: PaymentViewModel by viewModel()
    val args: PaymentFragmentArgs by navArgs()
    private lateinit var binding: PaymentFragmentBinding

    private lateinit var paymentMethodAdapter: PaymentMethodAdapter

    companion object {
        fun newInstance() = PaymentFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        paymentViewModel.setArguments(args)

        paymentViewModel.response.observe(this, Observer { result ->
            when (result.status) {
                Status.LOADING -> showLoading()
                Status.ERROR -> {
                    hideLoading()
                    processError(result.error)
                }
                Status.SUCCESS -> {
                    hideLoading()
                    navController.navigate(
                        PaymentFragmentDirections.actionPaymentFragmentToCheckoutFinalFragment(
                            args.cartResponse,
                            args.response,
                            args.details,
                            result.data
                        )
                    )
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PaymentFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        paymentViewModel.methods.observe(viewLifecycleOwner, Observer {
            paymentMethodAdapter.setItems(it)
        })

        paymentViewModel.selectedMethod.observe(viewLifecycleOwner, Observer {
            paymentMethodAdapter.setSelected(it)
        })
    }

    private fun initViews(): Unit = with(binding) {

        viewModel = paymentViewModel
        executePendingBindings()

        paymentMethodAdapter = PaymentMethodAdapter { paymentMethod, adapterPosition ->
            when (adapterPosition) {
                0 -> navigateToPaymentProviders(paymentMethod)
                else -> paymentViewModel.setSelectedMethod(paymentMethod)
            }
        }

        options.addItemDecoration(
            LineDividerItemDecoration(
                requireContext(),
                R.drawable.divider
            )
        )

        options.adapter = paymentMethodAdapter

        continueButton.setOnClickListener {
            paymentViewModel.buy()
        }
    }

    private fun navigateToPaymentProviders(paymentMethod: PaymentMethod) {
        PaymentFragmentDirections.actionPaymentFragmentToPaymentCardListFragment(
            args.cartResponse,
            args.response,
            args.details,
            PaymentData(
                null,
                args.response?.user?.phone,
                paymentViewModel.netPrice.value ?: 0,
                args.cartResponse?.products?.first()?.currencies?.first()?.currency ?: "UZS",
                paymentMethod
            )
        ).run { navController.navigate(this) }

    }

    private fun processError(error: ApiError?) = when (error?.code) {
        Const.API_NO_CONNECTION_STATUS_CODE -> navigateAndObserveResult(R.id.noInternetFragment)
        Const.API_SERVER_FAIL_STATUS_CODE -> navigateAndObserveResult(R.id.serverErrorDialogFragment)
        Const.API_NEW_VERSION_AVAILABLE_STATUS_CODE -> navController.navigate(R.id.newVersionAvailableFragmentDialog)
        else -> showError(error)
    }

    private fun navigateAndObserveResult(@IdRes destinationID: Int) {
        navController.getNavigationResult<Event<Boolean>>()?.observe(viewLifecycleOwner, Observer {
            if (it.getContentIfNotHandled() == true) paymentViewModel.buy()
        })
        navController.navigate(destinationID)
    }
}
