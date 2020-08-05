package uz.mod.templatex.ui.supportCenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.view_support_fragment_center_item.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.R
import uz.mod.templatex.databinding.SupportCenterFragmentBinding
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.utils.extension.lazyFast

class SupportCenterFragment : ParentFragment() {

    val viewModel: SupportCenterViewModel by viewModel()

    private val navController by lazyFast { findNavController() }
    private val binding by lazy { SupportCenterFragmentBinding.inflate(layoutInflater) }

    companion object {
        fun newInstance() = SupportCenterFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this@SupportCenterFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@SupportCenterFragment.viewModel
            executePendingBindings()

            // Init text resources
            orderStatus.titleTv.text = getString(R.string.support_center_item_order_status)
            payment.titleTv.text = getString(R.string.support_center_item_payment)
            returnConditions.titleTv.text = getString(R.string.support_center_item_return_conditions)
            deliveryTerms.titleTv.text = getString(R.string.support_center_item_delivery_terms)
            howToChooseSize.titleTv.text = getString(R.string.support_center_item_how_to_choose_size)
            howToPlaceOrder.titleTv.text = getString(R.string.support_center_item_how_to_place_order)
            offer.titleTv.text = getString(R.string.support_center_item_offer)
        }
    }
}
