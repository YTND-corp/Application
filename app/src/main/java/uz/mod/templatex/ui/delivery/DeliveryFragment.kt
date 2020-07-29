package uz.mod.templatex.ui.delivery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.databinding.DeliveryFragmentBinding
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.utils.extension.lazyFast


class DeliveryFragment : ParentFragment() {

    private val navController by lazyFast { findNavController() }
    val viewModel: DeliveryViewModel by viewModel()
    val args: DeliveryFragmentArgs by navArgs()

    private val binding by lazy { DeliveryFragmentBinding.inflate(layoutInflater) }

    private lateinit var deliveryOptionAdapter: DeliveryAdapter

    companion object {
        fun newInstance() = DeliveryFragment()
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
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        viewModel.options.observe(viewLifecycleOwner, Observer {
            deliveryOptionAdapter.setItems(it)
        })

        viewModel.selectedOption.observe(viewLifecycleOwner, Observer {
            deliveryOptionAdapter.setSelected(it)
        })
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@DeliveryFragment.viewModel
            executePendingBindings()

            deliveryOptionAdapter = DeliveryAdapter{
                viewModel?.selectedOption?.value = it
            }

            options.adapter = deliveryOptionAdapter


            continueButton.setOnClickListener {
                val detail = args.details
                detail?.delivery = viewModel?.selectedOption?.value
                navController.navigate(DeliveryFragmentDirections.actionDeliveryFragmentToPaymentFragment(args.response, detail))
            }
        }
    }
}