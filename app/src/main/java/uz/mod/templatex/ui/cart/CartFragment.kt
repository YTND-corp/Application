package uz.mod.templatex.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import uz.mod.templatex.R
import uz.mod.templatex.databinding.CartFragmentBinding
import uz.mod.templatex.model.local.entity.Product
import uz.mod.templatex.model.remote.network.Status
import uz.mod.templatex.ui.parent.ParentFragment


class CartFragment : ParentFragment(), CartAdapter.ItemListener {

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


        viewModel.response.observe(viewLifecycleOwner, Observer {result ->
            when(result.status) {
                Status.LOADING -> showLoading()
                Status.ERROR -> {
                    hideLoading()
                    showError(result.error)
                }
                Status.SUCCESS -> {
                    hideLoading()
                }
            }
        })

        viewModel.updateResponse.observe(viewLifecycleOwner, Observer {result ->
            when(result.status) {
                Status.LOADING -> showLoading()
                Status.ERROR -> {
                    hideLoading()
                    showError(result.error)
                }
                Status.SUCCESS -> {
                    hideLoading()
                }
            }
        })

        viewModel.deleteResponse.observe(viewLifecycleOwner, Observer {result ->
            when(result.status) {
                Status.LOADING -> showLoading()
                Status.ERROR -> {
                    hideLoading()
                    showError(result.error)
                }
                Status.SUCCESS -> {
                    hideLoading()
                }
            }
        })

        viewModel.products.observe(viewLifecycleOwner, Observer {result ->
           adapter.setItems(result)
        })

        viewModel.isEditing.observe(viewLifecycleOwner, Observer {
            adapter.setIsEditing(it)
        })

        viewModel.getCart()
    }

    private fun initViews() {

        adapter = CartAdapter(this)

        binding.apply {
            viewModel = this@CartFragment.viewModel
            executePendingBindings()

            continueButton.setOnClickListener {
                if (viewModel?.isEditing?.value==true) {
                    viewModel?.delete()
                } else {
                    findNavController().navigate(R.id.action_cartFragment_to_checkout_graph)
                }
            }

            placeholderButton.setOnClickListener {
                findNavController().navigate(R.id.action_cartFragment_to_checkout_graph)
            }

            products.adapter = adapter

            viewModel?.products?.observe(viewLifecycleOwner, Observer {
                adapter.setItems(it)
            })

        }
    }

    override fun select(product: Product) {
        viewModel.select(product)
    }

    override fun minus(product: Product) {
        viewModel.sub(product)
    }

    override fun plus(product: Product) {
        viewModel.add(product)
    }
}
