package uz.mod.templatex.ui.profile.authorized.myOrders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import androidx.annotation.IdRes
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.view_search.*
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

    private val binding by lazy { ProfileMyOrdersFragmentBinding.inflate(layoutInflater) }

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this@ProfileMyOrdersFragment
        return binding.root
    }

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

    override fun onDestroy() {
        super.onDestroy()
        activity?.window?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        )
    }

    private fun initViews(): Unit = with(binding) {
        viewModel = this@ProfileMyOrdersFragment.viewModel
        executePendingBindings()

        searchEt.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel?.getOrders(v.text.toString())
                return@setOnEditorActionListener true
            }
            false
        }

        myOrdersRv.adapter = adapter
        
    }

    private fun processError(error: ApiError?) {
        when (error?.code) {
            Const.API_NO_CONNECTION_STATUS_CODE -> navigateAndObserveResult(R.id.noInternetFragment)
            Const.API_SERVER_FAIL_STATUS_CODE -> navigateAndObserveResult(R.id.serverErrorDialogFragment)
            Const.API_NEW_VERSION_AVAILABLE_STATUS_CODE -> navController.navigate(R.id.newVersionAvailableFragmentDialog)
            else -> showError(error)
        }
    }

    private fun navigateAndObserveResult(@IdRes destinationID: Int) {
        navController.getNavigationResult<Event<Boolean>>()?.observe(viewLifecycleOwner, Observer {
            if (it.getContentIfNotHandled() == true) viewModel.getOrders()
        })
        navController.navigate(destinationID)
    }
}