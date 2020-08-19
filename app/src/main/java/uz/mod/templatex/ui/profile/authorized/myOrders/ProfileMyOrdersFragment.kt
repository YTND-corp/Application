package uz.mod.templatex.ui.profile.authorized.myOrders

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.R
import uz.mod.templatex.databinding.ProfileMyOrdersFragmentBinding
import uz.mod.templatex.model.remote.network.ApiError
import uz.mod.templatex.model.remote.network.Status
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.utils.Const
import uz.mod.templatex.utils.Event
import uz.mod.templatex.utils.extension.getNavigationResult
import uz.mod.templatex.utils.extension.lazyFast

class ProfileMyOrdersFragment : ParentFragment() {

    private val navController by lazyFast { findNavController() }
    val viewModel: ProfileMyOrdersViewModel by viewModel()

    private val binding: ProfileMyOrdersFragmentBinding
        get() = childBinding as ProfileMyOrdersFragmentBinding

    private val myOrdersAdapterListener = object : MyOrdersAdapterListener {
        override fun toMyOrder(orderId: Int) {
            val action =
                ProfileMyOrdersFragmentDirections.actionProfileMyOrdersFragmentToProfileMyOrderFragment(
                    orderId
                )
            navController.navigate(action)
        }
    }

    private var adapter = ProfileMyOrdersAdapter(myOrdersAdapterListener)

    companion object {
        fun newInstance() = ProfileMyOrdersFragment()
    }

    override fun getLayoutID() = R.layout.profile_my_orders_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

        viewModel.response.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.LOADING -> showLoading()
                Status.ERROR -> {
                    hideLoading()
                    processError(result.error)
                }
                Status.SUCCESS -> {
                    hideLoading()
                    adapter.setItems(result.data)
                }
            }
        })

        viewModel.getOrders()
    }

    private fun initViews(): Unit = with(binding) {
        viewModel = this@ProfileMyOrdersFragment.viewModel
        executePendingBindings()

        myOrdersRv.adapter = adapter
    }

    private fun processError(error: ApiError?) = when (error?.code) {
        Const.API_NO_CONNECTION_STATUS_CODE -> navigateAndObserveResult(R.id.noInternetFragment)
        Const.API_SERVER_FAIL_STATUS_CODE -> navigateAndObserveResult(R.id.serverErrorDialogFragment)
        Const.API_NEW_VERSION_AVAILABLE_STATUS_CODE -> navController.navigate(R.id.newVersionAvailableFragmentDialog)
        else -> showError(error)
    }


    private fun navigateAndObserveResult(@IdRes destinationID: Int) {
        navController.getNavigationResult<Event<Boolean>>()?.observe(viewLifecycleOwner, Observer {
            if (it.getContentIfNotHandled() == true) viewModel.getOrders()
        })
        navController.navigate(destinationID)
    }
}