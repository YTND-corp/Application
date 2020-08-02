package uz.mod.templatex.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.yanzhenjie.recyclerview.SwipeMenuItem
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.R
import uz.mod.templatex.databinding.CartFragmentBinding
import uz.mod.templatex.model.local.entity.Product
import uz.mod.templatex.model.remote.network.ApiError
import uz.mod.templatex.model.remote.network.Status
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.utils.Const
import uz.mod.templatex.utils.Event
import uz.mod.templatex.utils.extension.getNavigationResult
import uz.mod.templatex.utils.extension.lazyFast
import uz.mod.templatex.utils.extension.toPx


class CartFragment : ParentFragment(), CartAdapter.ItemListener {

    private val navController by lazyFast { findNavController() }
    val viewModel: CartViewModel by viewModel()
    private val binding by lazy { CartFragmentBinding.inflate(layoutInflater) }
    private lateinit var adapter: CartAdapter

    companion object {
        fun newInstance() = CartFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this@CartFragment
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
                Status.SUCCESS -> hideLoading()

            }
        })

        viewModel.updateResponse.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.LOADING -> showLoading()
                Status.ERROR -> {
                    hideLoading()
                    processError(result.error)
                }
                Status.SUCCESS -> hideLoading()
            }
        })

        viewModel.deleteResponse.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.LOADING -> showLoading()
                Status.ERROR -> {
                    hideLoading()
                    processError(result.error)
                }
                Status.SUCCESS -> hideLoading()
            }
        })

        viewModel.products.observe(viewLifecycleOwner, Observer { result ->
            adapter.setItems(result)
        })

        viewModel.totalPrice.observe(viewLifecycleOwner, Observer {
            binding.totalPrice.text = it
        })

        viewModel.getCart()
    }

    private fun initViews(): Unit = with(binding) {
        adapter = CartAdapter(this@CartFragment)
        viewModel = this@CartFragment.viewModel
        executePendingBindings()

        products.adapter = null

        products.setSwipeMenuCreator { _, rightMenu, _ ->
            rightMenu.addMenuItem(
                SwipeMenuItem(activity)
                    .setBackground(R.color.black)
                    .setImage(R.drawable.ic_trash)
                    .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)
                    .setWidth(150.toPx())
            )
        }

        products.setOnItemMenuClickListener { menuBridge, adapterPosition ->
            menuBridge.closeMenu()

            viewModel?.products?.value?.get(adapterPosition)?.cartProductId?.let {
                viewModel?.delete(it)
            }
        }

        continueButton.setOnClickListener {
            navController.navigate(R.id.action_cartFragment_to_checkout_graph)
        }

        placeholderButton.setOnClickListener {
            navController.navigate(R.id.action_cartFragment_to_checkout_graph)
        }

        products.adapter = adapter

        viewModel?.products?.observe(viewLifecycleOwner, Observer {
            adapter.setItems(it)
        })
    
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
            if (it.getContentIfNotHandled() == true) viewModel.getCart()
        })
        navController.navigate(destinationID)
    }

    override fun minus(product: Product) {
        viewModel.sub(product)
    }

    override fun plus(product: Product) {
        viewModel.add(product)
    }

    override fun favoriteToggle(product: Product) {
        viewModel.favoriteToggle(product.id)
            .observe(viewLifecycleOwner, Observer { result ->
                when (result.status) {
                    Status.LOADING -> showLoading()
                    Status.ERROR -> {
                        hideLoading()
                        processError(result.error)
                    }
                    Status.SUCCESS -> {
                        hideLoading()
                    }
                }
            })
    }
}
