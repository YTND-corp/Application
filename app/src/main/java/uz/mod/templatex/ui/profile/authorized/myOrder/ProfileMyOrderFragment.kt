package uz.mod.templatex.ui.profile.authorized.myOrder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.R
import uz.mod.templatex.databinding.ProfileMyOrderFragmentBinding
import uz.mod.templatex.model.remote.network.ApiError
import uz.mod.templatex.model.remote.network.Status
import uz.mod.templatex.model.remote.response.profile.StateType
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.utils.Const
import uz.mod.templatex.utils.Event
import uz.mod.templatex.utils.extension.getNavigationResult
import uz.mod.templatex.utils.extension.lazyFast

class ProfileMyOrderFragment : ParentFragment() {

    private val navController by lazyFast { findNavController() }
    val viewModel: ProfileMyOrderViewModel by viewModel()
    val args: ProfileMyOrderFragmentArgs by navArgs()

    private val binding by lazy { ProfileMyOrderFragmentBinding.inflate(layoutInflater) }
    lateinit var adapter: ProductAdapter

    companion object {
        fun newInstance() = ProfileMyOrderFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = ProductAdapter()
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

        viewModel.getOrder(args.orderId).observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.LOADING -> showLoading()
                Status.ERROR -> {
                    hideLoading()
                    processError(result.error)
                }
                Status.SUCCESS -> {
                    hideLoading()

                    when (result.data?.order?.state?.type) {
                        StateType.INFO -> binding.myOrderStatusView.setBackgroundResource(R.drawable.my_orders_indicator_blue)
                        StateType.WARNING -> binding.myOrderStatusView.setBackgroundResource(R.drawable.my_orders_indicator_yellow)
                        StateType.SUCCESS -> binding.myOrderStatusView.setBackgroundResource(R.drawable.my_orders_indicator_green)
                        StateType.DANGER -> binding.myOrderStatusView.setBackgroundResource(R.drawable.my_orders_indicator_red)
                    }

                    sharedViewModel.setTitle(result.data?.order?.printOrderId())
                    binding.item = result.data
                    adapter.setItems(result.data?.cart?.products)
                    viewModel.isFirstTimeVisible.value = true
                }
            }
        })
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@ProfileMyOrderFragment.viewModel
            executePendingBindings()

            rvProducts.adapter = adapter
        }
    }

    //TODO("PLEASE CHECK HERE IF THE LOGIC IS NOT BROKEN")
    private fun processError(error: ApiError?) = when (error?.code) {
        Const.API_NO_CONNECTION_STATUS_CODE -> navController.navigate(R.id.noInternetFragment)
        Const.API_SERVER_FAIL_STATUS_CODE -> navController.navigate(R.id.serverErrorDialogFragment)
        Const.API_NEW_VERSION_AVAILABLE_STATUS_CODE -> navController.navigate(R.id.newVersionAvailableFragmentDialog)
        else -> showError(error)
    }

}