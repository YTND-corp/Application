package uz.mod.templatex.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.products_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import uz.mod.templatex.R
import uz.mod.templatex.databinding.ProductsFragmentBinding
import uz.mod.templatex.model.remote.network.Status
import uz.mod.templatex.ui.parent.ParentFragment


class ProductsFragment : ParentFragment() {

    val viewModel: ProductsViewModel by viewModel()

    private val binding by lazy { ProductsFragmentBinding.inflate(layoutInflater) }

    private lateinit var adapter: ProductAdapter

    private val args: ProductsFragmentArgs by navArgs()

    var isLoadingMore = false

    companion object {
        fun newInstance() = ProductsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = ProductAdapter { id, isFavorite ->
            viewModel.favoriteToggle(id).observe(viewLifecycleOwner, Observer { result ->
                    when (result.status) {
                        Status.LOADING -> showLoading()
                        Status.ERROR -> {
                            hideLoading()
                            showError(result.error)
                        }
                        Status.SUCCESS -> {
                            hideLoading()
                            Timber.e(result.data.toString())
                        }
                    }
                })
        }

        viewModel.response.observe(this, Observer { result ->
            when (result.status) {
                Status.LOADING -> showLoading()
                Status.ERROR -> {
                    hideLoading()
                    showError(result.error)
                }
                Status.SUCCESS -> {
                    hideLoading()
                    adapter.setItems(result.data)
                }
            }
        })

        viewModel.setArgs(args)
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

        viewModel.response.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.LOADING -> {
                    if (!isLoadingMore) showLoading()
                }
                Status.ERROR -> {
                    isLoadingMore = false
                    hideLoading()
                    showError(result.error)
                }
                Status.SUCCESS -> {
                    isLoadingMore = false
                    hideLoading()
                    if (viewModel.page==1) {
                        binding.subtitle.text = getString(R.string.products_subtitle, result.data.toString() )
                    }
                }
            }
        })
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@ProductsFragment.viewModel
            executePendingBindings()

            filter.setOnClickListener {
               // findNavController().navigate(R.id.action_productsFragment_to_filterFragment)
            }

            val layoutManager = GridLayoutManager(requireContext(),2)
            products.layoutManager = layoutManager
            products.adapter = adapter


            var pastVisiblesItems: Int
            var visibleItemCount: Int
            var totalItemCount: Int

            products.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0) {
                        visibleItemCount = layoutManager.childCount
                        totalItemCount = layoutManager.itemCount
                        pastVisiblesItems = layoutManager.findFirstVisibleItemPosition()
                        if (!isLoadingMore) {
                            if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                                Timber.e("LoadingMore..........")
                                isLoadingMore = true
                                viewModel?.loadMore()
                            }
                        }
                    }
                }
            })

            sortWrapper.setOnClickListener {

            }

            back.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}