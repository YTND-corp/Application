package uz.mod.templatex.ui.checkoutFinal

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.R
import uz.mod.templatex.databinding.CheckoutFinalFragmentBinding
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.utils.Const
import uz.mod.templatex.utils.extension.lazyFast


class CheckoutFinalFragment : ParentFragment() {

    private val navController by lazyFast { findNavController() }
    private val checkoutFinalViewModel: CheckoutFinalViewModel by viewModel()
    private lateinit var productAdapter: ProductAdapter
    private lateinit var priceAdapter: PriceAdapter
    private val binding by lazy { CheckoutFinalFragmentBinding.inflate(layoutInflater) }
    val args: CheckoutFinalFragmentArgs by navArgs()

    companion object {
        fun newInstance() = CheckoutFinalFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.checkout_final_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_exit -> {
                navController.popBackStack(R.id.cartFragment, false)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkoutFinalViewModel.setArgs(args)
        productAdapter = ProductAdapter(checkoutFinalViewModel)
        priceAdapter = PriceAdapter(checkoutFinalViewModel)

        initViews()

        requireActivity().onBackPressedDispatcher.addCallback(this.viewLifecycleOwner, true) {
            navController.popBackStack(R.id.cartFragment, false)
            remove()
        }
    }

    private fun initViews(): Unit = with(binding) {
        viewModel = checkoutFinalViewModel
        executePendingBindings()

        rvProducts.adapter = productAdapter
        rvPrices.adapter = priceAdapter
        tvDescription.text = getString(R.string.checkout_description_text, args.storeResponse?.order?.id.toString())
        tvDeliveryType.text = getString(R.string.delivery_type_text, checkoutFinalViewModel.getDeliveryType())

        btnCallback.setOnClickListener {
            navController.navigate(R.id.action_checkoutFinalFragment_to_callMeFragment)
        }

        btnCallUs.setOnClickListener {
            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:${Const.PHONE_NUMBER}")))
        }

        productAdapter.setItems(args.cartResponse?.products)
        priceAdapter.setItems(args.cartResponse?.products)
    }
}
