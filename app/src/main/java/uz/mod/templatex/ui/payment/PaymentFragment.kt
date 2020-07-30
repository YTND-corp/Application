package uz.mod.templatex.ui.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import uz.mod.templatex.R
import uz.mod.templatex.databinding.PaymentFragmentBinding
import uz.mod.templatex.model.remote.network.ApiError
import uz.mod.templatex.model.remote.network.Status
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.utils.Const
import uz.mod.templatex.utils.Event
import uz.mod.templatex.utils.extension.getNavigationResult
import uz.mod.templatex.utils.extension.lazyFast

class PaymentFragment : ParentFragment() {

    private val navController by lazyFast { findNavController() }
    val viewModel: PaymentViewModel by viewModel()
    val args: PaymentFragmentArgs by navArgs()
    private val binding by lazy { PaymentFragmentBinding.inflate(layoutInflater) }

    private lateinit var paymentMethodAdapter: PaymentMethodAdapter

    companion object {
        fun newInstance() = PaymentFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setArguments(args)

        viewModel.response.observe(this, Observer { result ->
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
                        PaymentFragmentDirections.actionPaymentFragmentToCheckoutFinalFragment(
                            result.data?.user?.name,
                            result.data?.date
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
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        viewModel.methods.observe(viewLifecycleOwner, Observer {
            paymentMethodAdapter.setItems(it)
        })

        viewModel.selectedMethod.observe(viewLifecycleOwner, Observer {
            paymentMethodAdapter.setSelected(it)
        })
    }

    private fun initViews(): Unit = with(binding) {

        viewModel = this@PaymentFragment.viewModel
        executePendingBindings()

        paymentMethodAdapter = PaymentMethodAdapter {
            viewModel?.setSelectedMethod(it)
        }

        options.adapter = paymentMethodAdapter

        continueButton.setOnClickListener {
            this@PaymentFragment.viewModel.buy()
        }
    }

    private fun processError(error: ApiError?) {
        when (error?.code) {
            Const.API_NO_CONNECTION_STATUS_CODE -> {
                navController.getNavigationResult<Event<Boolean>>()?.observe(viewLifecycleOwner, Observer {
                    if (it.getContentIfNotHandled() == true) viewModel.buy()
                })
                navController.navigate(R.id.noInternetFragment)
            }
            Const.API_SERVER_FAIL_STATUS_CODE -> {
                navController.getNavigationResult<Event<Boolean>>()?.observe(viewLifecycleOwner, Observer {
                    if (it.getContentIfNotHandled() == true) viewModel.buy()
                })
                navController.navigate(R.id.serverErrorDialogFragment)
            }
            else -> showError(error)
        }
    }
}
