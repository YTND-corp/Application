package uz.mod.templatex.ui.paymentProvider

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_payment_cards.*
import uz.mod.templatex.R
import uz.mod.templatex.ui.custom.LineDividerItemDecoration
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.utils.extension.lazyFast

class PaymentProviderFragment : ParentFragment() {

    private val navController by lazyFast { findNavController() }
    private val args: PaymentProviderFragmentArgs by navArgs()

    private val paymentProviderAdapter by lazyFast {
        PaymentProviderAdapter(args.response?.payment?.providers) {
            args.paymentData.provider = it
            PaymentProviderFragmentDirections.actionPaymentProviderFragmentToPaymentDetailsFragment(
                args.cartResponse,
                args.response,
                args.details,
                args.paymentData
            ).run { navController.navigate(this) }
        }
    }

    override fun getLayoutID(): Int? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_payment_cards, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        paymentCardsRecyclerView.adapter = paymentProviderAdapter
        paymentCardsRecyclerView.addItemDecoration(
            LineDividerItemDecoration(
                requireContext(),
                R.drawable.divider
            )
        )
    }

}