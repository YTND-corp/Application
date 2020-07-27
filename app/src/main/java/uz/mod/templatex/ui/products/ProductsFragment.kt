package uz.mod.templatex.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import uz.mod.templatex.R
import uz.mod.templatex.databinding.ProductsFragmentBinding
import uz.mod.templatex.model.remote.network.Status
import uz.mod.templatex.ui.new_filter.MainFilterFragment
import uz.mod.templatex.ui.new_filter.SharedFilterViewModel
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.utils.extension.toast


class ProductsFragment : ParentFragment() {
    private val sharedFilterViewModel: SharedFilterViewModel by activityViewModels()
    val viewModel: ProductsViewModel by viewModel()

    private val binding by lazy { ProductsFragmentBinding.inflate(layoutInflater) }

    private lateinit var adapter: ProductAdapter
    private lateinit var mBrandAdapter: BrandAdapter

    private val args: ProductsFragmentArgs by navArgs()

    var isLoadingMore = false

    companion object {
        fun newInstance() = ProductsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = ProductAdapter { product, position ->
            viewModel.favoriteToggle(product.id).observe(viewLifecycleOwner, Observer { result ->
                when (result.status) {
                    Status.LOADING -> showLoading()
                    Status.ERROR -> {
                        hideLoading()
                        showError(result.error)
                    }
                    Status.SUCCESS -> {
                        hideLoading()
                        adapter.updateItem(product, position)
                        showFavouriteStatus(product.isFavorite)
                        Timber.e(result.data.toString())
                    }
                }
            })
        }

        mBrandAdapter = BrandAdapter {
            findNavController().navigate(ProductsFragmentDirections.actionGlobalProductsFragment(it.id, it.name))
        }

        viewModel.setArgs(args)
    }

    private fun showFavouriteStatus(isFavorite: Boolean) {
        val message = if (isFavorite) "Added to favourite" else "Removed from favourite"
        requireActivity().toast(message)
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

        viewModel.filterParams = sharedFilterViewModel.activeFilter
        viewModel.response.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { result ->
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
                        if (viewModel.page == 1) {
                            val string =
                                resources.getString(R.string.products_subtitle, result.data?.size.toString())
                            Timber.d("binding.subtitle.text $string")
                            adapter.setItems(result.data)
                        } else {
                            adapter.addItems(result.data)
                        }
                    }
                }
            }
        })

        viewModel.brands.observe(viewLifecycleOwner, Observer {
            mBrandAdapter.setItems(it)
        })
    }

    override fun onResume() {
        super.onResume()
        if (sharedFilterViewModel.needToReloadFeed) {
            sharedFilterViewModel.needToReloadFeed = false
            sharedFilterViewModel.fillActiveFilter()
            viewModel.filterParams = sharedFilterViewModel.activeFilter
            viewModel.refresh()
        }
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@ProductsFragment.viewModel
            executePendingBindings()

            sort.text = sharedFilterViewModel.activeFilter.sort.name
            val layoutManager = GridLayoutManager(requireContext(), 2)
            products.layoutManager = layoutManager
            products.adapter = adapter
            rvBrands.adapter = mBrandAdapter

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

            filter.setOnClickListener {
                viewModel?.categoryId?.let {
                    findNavController().navigate(
                        R.id.mainFilterFragment,
                        bundleOf(MainFilterFragment.KEY_CATEGORY_ID to it)
                    )
                }
            }

            sortWrapper.setOnClickListener {
                viewModel?.categoryId?.let {
                    findNavController().navigate(R.id.sortFragment)
                }
            }

            back.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}