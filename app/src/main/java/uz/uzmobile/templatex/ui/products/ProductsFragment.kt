package uz.uzmobile.templatex.ui.products

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import uz.uzmobile.templatex.databinding.ProductsFragmentBinding
import uz.uzmobile.templatex.ui.favorite.ProductAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


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

            adapter = ProductAdapter(arrayListOf())
            products.hasFixedSize()
            products.adapter = adapter
        }
    }
}