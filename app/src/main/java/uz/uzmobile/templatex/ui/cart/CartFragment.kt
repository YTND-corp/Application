package uz.uzmobile.templatex.ui.cart

import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.yanzhenjie.recyclerview.SwipeMenuItem
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import uz.uzmobile.templatex.R
import uz.uzmobile.templatex.databinding.CartFragmentBinding
import uz.uzmobile.templatex.extension.color
import uz.uzmobile.templatex.extension.drawable
import uz.uzmobile.templatex.model.remote.network.Status
import uz.uzmobile.templatex.ui.parent.ParentFragment


class CartFragment : ParentFragment() {

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


        viewModel.getCart().observe(viewLifecycleOwner, Observer {result ->
            when(result.status) {
                Status.LOADING -> showLoading()
                Status.ERROR -> {
                    hideLoading()
                    showError(result.error)
                }
                Status.SUCCESS -> {
                    hideLoading()
                    Timber.e(result.data?.cart.toString())
                    viewModel.setCart(result.data?.cart)
                    viewModel.setProducts(result.data?.cart?.products)
                }
            }
        })

        viewModel.isEditing.observe(viewLifecycleOwner, Observer {
            adapter.setIsEditing(it)
        })

        viewModel.getCart()
    }

    private fun initViews() {

        adapter = CartAdapter({ viewModel.select(it) }, {
            viewModel.sub(it).observe(viewLifecycleOwner, Observer { result ->
                when (result.status) {
                    Status.LOADING -> showLoading()
                    Status.ERROR -> {
                        hideLoading()
                        showError(result.error)
                    }
                    Status.SUCCESS -> {
                        hideLoading()
                        viewModel.substracted(it)
                    }
                }
            })
        }, {
            viewModel.add(it).observe(viewLifecycleOwner, Observer { result ->
                when (result.status) {
                    Status.LOADING -> showLoading()
                    Status.ERROR -> {
                        hideLoading()
                        showError(result.error)
                    }
                    Status.SUCCESS -> {
                        hideLoading()
                        viewModel.added(it)
                    }
                }
            })
        })

        binding.apply {
            viewModel = this@CartFragment.viewModel
            executePendingBindings()

            continueButton.setOnClickListener {
                findNavController().navigate(R.id.action_cartFragment_to_checkoutFragment)
            }

            if (products.adapter==null) {
//                products.isSwipeItemMenuEnabled = true
//                products.setSwipeMenuCreator { leftMenu, rightMenu, position ->
//
//                    val height = ViewGroup.LayoutParams.MATCH_PARENT
//
//                    val item = SwipeMenuItem(requireContext())
//                        .setBackground(requireContext().drawable(R.drawable.bg_cart_menu_item_delete))
//                        .setText("Delete")
//                        .setTextColor(requireContext().color(R.color.whiteColor))
//                        .setHeight(height)
//                    rightMenu.addMenuItem(item)
//                }

//                products.setOnItemMenuClickListener { _, adapterPosition ->
//                    viewModel?.removeProduct(adapter.getItem(adapterPosition))?.observe(viewLifecycleOwner, Observer {result ->
//                        when(result.status) {
//                            Status.LOADING -> showLoading()
//                            Status.ERROR -> {
//                                hideLoading()
//                                showError(result.error)
//                            }
//                            Status.SUCCESS -> {
//                                hideLoading()
//                                Timber.e(result.data?.cart.toString())
//                                viewModel?.setCart(result.data?.cart)
//                                viewModel?.setProducts(result.data?.cart?.products)
//                            }
//                        }
//                    })
//                }
            }

            products.adapter = adapter

            viewModel?.products?.observe(viewLifecycleOwner, Observer {
                adapter.setItems(it)
            })

        }
    }
}
