package uz.mod.templatex.ui.profile.authorized.myOrder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.R
import uz.mod.templatex.databinding.ProfileMyOrderFragmentBinding
import uz.mod.templatex.model.remote.network.Status
import uz.mod.templatex.model.remote.response.profile.StateType
import uz.mod.templatex.ui.parent.ParentFragment

class ProfileMyOrderFragment : ParentFragment() {

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
        binding.lifecycleOwner = this@ProfileMyOrderFragment
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
                    showError(result.error)
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
}