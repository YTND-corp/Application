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
import uz.mod.templatex.databinding.PaymentFragmentBinding
import uz.mod.templatex.model.remote.network.Status
import uz.mod.templatex.ui.parent.ParentFragment

class PaymentFragment : ParentFragment() {

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

        viewModel.responce.observe(this, Observer { result ->
            when (result.status) {
                Status.LOADING -> showLoading()
                Status.ERROR -> {
                    hideLoading()
                    showError(result.error)
                }
                Status.SUCCESS -> {
                    hideLoading()
                    Timber.e(result.data.toString())
                    findNavController().navigate(PaymentFragmentDirections.actionPaymentFragmentToCheckoutFinalFragment(result.data?.user?.name, result.data?.date))
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

    private fun initViews() {
        binding.apply {
            viewModel = this@PaymentFragment.viewModel
            executePendingBindings()

            paymentMethodAdapter = PaymentMethodAdapter(){
                viewModel?.setSelectedMethod(it)
            }

            options.adapter = paymentMethodAdapter

            continueButton.setOnClickListener {
                viewModel?.buy()
            }

        }
    }
}
