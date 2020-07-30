package uz.mod.templatex.ui.supportCenter

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.view_support_fragment_center_item.view.*
import kotlinx.android.synthetic.main.view_support_fragment_center_item_black.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.R
import uz.mod.templatex.databinding.SupportCenterFragmentBinding
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.utils.Const
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
                navController.navigate(R.id.action_supportCenterFragment_to_supportCenterDetailsFragment)
            }
            deliveryItem.titleTv.setOnClickListener {
                // TODO
                navController.navigate(R.id.action_supportCenterFragment_to_supportCenterDetailsFragment)
            }
            returnItem.titleTv.setOnClickListener {
                // TODO
                navController.navigate(R.id.action_supportCenterFragment_to_supportCenterDetailsFragment)
            }
            salesItem.blackTitleTv.setOnClickListener {
                // TODO
                navController.navigate(R.id.action_supportCenterFragment_to_supportCenterDetailsFragment)
            }
            orderItem.titleTv.setOnClickListener {
                // TODO
                navController.navigate(R.id.action_supportCenterFragment_to_supportCenterDetailsFragment)
            }
            goodsItem.titleTv.setOnClickListener {
                // TODO
                navController.navigate(R.id.action_supportCenterFragment_to_supportCenterDetailsFragment)
            }
            paymentItem.titleTv.setOnClickListener {
                // TODO
                navController.navigate(R.id.action_supportCenterFragment_to_supportCenterDetailsFragment)
            }
            partnersGoodsItem.titleTv.setOnClickListener {
                // TODO
                navController.navigate(R.id.action_supportCenterFragment_to_supportCenterDetailsFragment)
            }
            profileAndSubscriptionsItem.titleTv.setOnClickListener {
                // TODO
                navController.navigate(R.id.action_supportCenterFragment_to_supportCenterDetailsFragment)
            }
            workAndCollaborationItem.titleTv.setOnClickListener {
                // TODO
                navController.navigate(R.id.action_supportCenterFragment_to_supportCenterDetailsFragment)
            }
            reviewsItem.titleTv.setOnClickListener {
                // TODO
                navController.navigate(R.id.action_supportCenterFragment_to_supportCenterDetailsFragment)
            }

            // Bottom Buttons
            emailUsBtn.setOnClickListener {
                navController.navigate(R.id.action_supportCenterFragment_to_askQuestionFragment)
            }
            callUsBtn.setOnClickListener {
                startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:${Const.PHONE_NUMBER}")))
            }
        }
    }
}
