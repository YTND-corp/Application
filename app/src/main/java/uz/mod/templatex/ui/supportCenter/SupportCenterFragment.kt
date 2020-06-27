package uz.mod.templatex.ui.supportCenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.view_support_fragment_center_item.view.*
import kotlinx.android.synthetic.main.view_support_fragment_center_item_black.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.R
import uz.mod.templatex.databinding.SupportCenterFragmentBinding

class SupportCenterFragment : Fragment() {

    val viewModel: SupportCenterViewModel by viewModel()

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
            newUsersItem.titleTv.text = getString(R.string.support_center_item_new_users)
            deliveryItem.titleTv.text = getString(R.string.support_center_item_delivery)
            returnItem.titleTv.text = getString(R.string.support_center_item_return)
            salesItem.blackTitleTv.text = getString(R.string.support_center_item_sales)
            orderItem.titleTv.text = getString(R.string.support_center_item_order)
            goodsItem.titleTv.text = getString(R.string.support_center_item_goods)
            paymentItem.titleTv.text = getString(R.string.support_center_item_payment)
            partnersGoodsItem.titleTv.text = getString(R.string.support_center_item_partners_goods)
            profileAndSubscriptionsItem.titleTv.text =
                getString(R.string.support_center_item_profile_and_subscriptions)
            workAndCollaborationItem.titleTv.text =
                getString(R.string.support_center_item_work_and_collaborations)
            reviewsItem.titleTv.text = getString(R.string.support_center_item_reviews)

            newUsersItem.titleTv.setOnClickListener {
                // TODO
            }
            deliveryItem.titleTv.setOnClickListener {
                // TODO
            }
            returnItem.titleTv.setOnClickListener {
                // TODO
            }
            salesItem.titleTv.setOnClickListener {
                // TODO
            }
            orderItem.titleTv.setOnClickListener {
                // TODO
            }
            goodsItem.titleTv.setOnClickListener {
                // TODO
            }
            paymentItem.titleTv.setOnClickListener {
                // TODO
            }
            partnersGoodsItem.titleTv.setOnClickListener {
                // TODO
            }
            profileAndSubscriptionsItem.titleTv.setOnClickListener {
                // TODO
            }
            workAndCollaborationItem.titleTv.setOnClickListener {
                // TODO
            }
            reviewsItem.titleTv.setOnClickListener {
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
