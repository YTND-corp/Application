package uz.mod.templatex.ui.checkoutFinal

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    val viewModel: CheckoutFinalViewModel by viewModel()
    lateinit var productAdapter: ProductAdapter
    lateinit var priceAdapter: PriceAdapter

    private val binding by lazy { CheckoutFinalFragmentBinding.inflate(layoutInflater) }

    val args: CheckoutFinalFragmentArgs by navArgs()

    companion object {
        fun newInstance() = CheckoutFinalFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productAdapter = ProductAdapter()
        priceAdapter = PriceAdapter()
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

        requireActivity().onBackPressedDispatcher.addCallback(this.viewLifecycleOwner, true) {
            navController.popBackStack(R.id.cartFragment, false)
            remove()
        }
    }

    private fun initViews(): Unit = with(binding) {
        viewModel = this@CheckoutFinalFragment.viewModel
        executePendingBindings()

        title.text = getString(R.string.checkout_final_title, args.name)
        message.text = getString(R.string.checkout_final_message, args.date)
        closeButton.setOnClickListener {
            navController.popBackStack(R.id.checkoutFragment, true)
            rvProducts.adapter = productAdapter
            rvPrices.adapter = priceAdapter

            btnEmailUs.setOnClickListener {
                navController.navigate(R.id.action_checkoutFinalFragment_to_askQuestionFragment)
            }

            btnCallUs.setOnClickListener {
                startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:${Const.PHONE_NUMBER}")))
            }

            /*title.text = getString(R.string.checkout_final_title, args.name)
            message.text = getString(R.string.checkout_final_message, args.date)
            closeButton.setOnClickListener {
                navController.popBackStack(R.id.checkoutFragment, true)
            }*/
        }
    }
}
