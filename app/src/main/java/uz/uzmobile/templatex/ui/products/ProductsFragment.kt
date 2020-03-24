package uz.uzmobile.templatex.ui.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import uz.uzmobile.templatex.databinding.ProductsFragmentBinding
import uz.uzmobile.templatex.ui.favorite.ProductAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.uzmobile.templatex.R
import uz.uzmobile.templatex.model.local.entity.Product


class ProductsFragment : Fragment() {

    val viewModel: ProductsViewModel by viewModel()

    private val binding by lazy { ProductsFragmentBinding.inflate(layoutInflater) }

    private lateinit var adapter: ProductAdapter

    companion object {
        fun newInstance() = ProductsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this@ProductsFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        viewModel.products.observe(requireActivity(), Observer {
            if (!it.isNullOrEmpty()) {
                adapter.setItems(it)
                adapter.notifyDataSetChanged()
            }
        })
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@ProductsFragment.viewModel
            executePendingBindings()

            filter.setOnClickListener {
                findNavController().navigate(R.id.action_productsFragment_to_filterFragment)
            }

            adapter = ProductAdapter(arrayListOf(), object : ProductAdapter.ItemClickListener {
                override fun onClick(item: Product) {
                    findNavController().navigate(R.id.action_global_productFragment)
                }
            })

            products.hasFixedSize()
            products.adapter = adapter

            back.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}