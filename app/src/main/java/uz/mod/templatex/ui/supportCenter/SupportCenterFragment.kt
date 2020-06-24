package uz.mod.templatex.ui.supportCenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.R
import uz.mod.templatex.databinding.SupportCenterFragmentBinding

class SupportCenterFragment : Fragment() {

    val viewModel: SupportViewModel by viewModel()

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

            // Top Round icons
            locationImgContainer.setOnClickListener {
                // TODO
            }

            historyImgContainer.setOnClickListener {
                // TODO
            }
            meterImgContainer.setOnClickListener {
                // TODO
            }

            // Init text resources
            newUsersItem.findViewById<TextView>(R.id.titleTv).text =
                getString(R.string.support_center_item_new_users)
            deliveryItem.findViewById<TextView>(R.id.titleTv).text =
                getString(R.string.support_center_item_delivery)
            returnItem.findViewById<TextView>(R.id.titleTv).text =
                getString(R.string.support_center_item_return)
            salesItem.findViewById<TextView>(R.id.titleTv).text =
                getString(R.string.support_center_item_sales)
            orderItem.findViewById<TextView>(R.id.titleTv).text =
                getString(R.string.support_center_item_order)
            goodsItem.findViewById<TextView>(R.id.titleTv).text =
                getString(R.string.support_center_item_goods)
            paymentItem.findViewById<TextView>(R.id.titleTv).text =
                getString(R.string.support_center_item_payment)
            partnersGoodsItem.findViewById<TextView>(R.id.titleTv).text =
                getString(R.string.support_center_item_partners_goods)
            profileAndSubscriptionsItem.findViewById<TextView>(R.id.titleTv).text =
                getString(R.string.support_center_item_profile_and_subscriptions)
            workAndCollaborationItem.findViewById<TextView>(R.id.titleTv).text =
                getString(R.string.support_center_item_work_and_collaborations)
            reviewsItem.findViewById<TextView>(R.id.titleTv).text =
                getString(R.string.support_center_item_reviews)

            newUsersItem.setOnClickListener {
                // TODO
            }
            deliveryItem.setOnClickListener {
                // TODO
            }
            returnItem.setOnClickListener {
                // TODO
            }
            salesItem.setOnClickListener {
                // TODO
            }
            orderItem.setOnClickListener {
                // TODO
            }
            goodsItem.setOnClickListener {
                // TODO
            }
            paymentItem.setOnClickListener {
                // TODO
            }
            partnersGoodsItem.setOnClickListener {
                // TODO
            }
            profileAndSubscriptionsItem.setOnClickListener {
                // TODO
            }
            workAndCollaborationItem.setOnClickListener {
                // TODO
            }
            reviewsItem.setOnClickListener {
                // TODO
            }

            // Bottom Buttons
            emailUsBtn.setOnClickListener {
                // TODO
            }

            callUsBtn.setOnClickListener {
                // TODO
            }
        }
    }
}
