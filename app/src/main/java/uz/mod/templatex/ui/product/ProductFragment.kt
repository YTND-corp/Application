package uz.mod.templatex.ui.product

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import kotlinx.android.synthetic.main.product_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import uz.mod.templatex.R
import uz.mod.templatex.databinding.ProductFragmentBinding
import uz.mod.templatex.model.local.entity.Product
import uz.mod.templatex.model.remote.network.ApiError
import uz.mod.templatex.model.remote.network.Status
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.ui.product.adapter.*
import uz.mod.templatex.utils.Const
import uz.mod.templatex.utils.Event
import uz.mod.templatex.utils.SnapOnScrollListener
import uz.mod.templatex.utils.extension.attachSnapHelperWithListener
import uz.mod.templatex.utils.extension.getNavigationResult
import uz.mod.templatex.utils.extension.lazyFast
import uz.mod.templatex.utils.extension.toPx

class ProductFragment : ParentFragment() {

    private val navController by lazyFast { findNavController() }
    private val productViewModel: ProductViewModel by viewModel()
    private val binding by lazy { ProductFragmentBinding.inflate(layoutInflater) }
    private val args: ProductFragmentArgs by navArgs()

    private var indicatorAdapter = ProductBannerIndicatorAdapter()
    private lateinit var bannerAdapter: ProductBannerAdapter
    private lateinit var colorAdapter: ProductColorAdapter
    private lateinit var sizeAdapter: ProductSizeAdapter
    private lateinit var relativeProductAdapter: ProductHorizontalAdapter

    companion object {
        const val IMAGE_POSITION = "ProductFragment.IMAGE_POSITION"

        fun newInstance() = ProductFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productViewModel.setArgs(args)
        sizeAdapter = ProductSizeAdapter {
            productViewModel.setSelectedSize(it)
        }
        colorAdapter = ProductColorAdapter {
            productViewModel.setSelectedColor(it)
            indicatorAdapter.setSelected(0)
            banners.scrollToPosition(0)
        }
        bannerAdapter = ProductBannerAdapter { items, position ->
            val direction = ProductFragmentDirections.actionProductFragmentToFullScreenImageFragment(
                items.toTypedArray(),
                position
            )
            navController.navigate(direction)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this@ProductFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

        productViewModel.response.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.LOADING -> showLoading()
                Status.ERROR -> {
                    hideLoading()
                    processError(result.error)
                }
                Status.SUCCESS -> {
                    hideLoading()
                    if (result.data?.product?.currencies?.first()?.discount ?: 0 > 0)
                        price.setTextColor(ContextCompat.getColor(price.context, R.color.red))
                    else price.setTextColor(ContextCompat.getColor(price.context, R.color.blackTextColor))
                }
            }
        })

        productViewModel.colors.observe(viewLifecycleOwner, Observer {
            if (productViewModel.selectedColor.value == null)
                productViewModel.setSelectedColor(it?.firstOrNull())
            colorAdapter.setItems(it)
        })

        productViewModel.shouldShowColors.observe(viewLifecycleOwner, Observer {
            val visibility = if (it == true) View.VISIBLE else View.GONE
            tvColor.visibility = visibility
            rvColors.visibility = visibility
        })

        productViewModel.sizes.observe(viewLifecycleOwner, Observer {
            sizeAdapter.setItems(it)
        })

        productViewModel.shouldShowSize.observe(viewLifecycleOwner, Observer {
            val visibility = if (it == true) View.VISIBLE else View.GONE
            sizes.visibility = visibility
            size_header.visibility = visibility
        })

        productViewModel.selectedColor.observe(viewLifecycleOwner, Observer {
            colorAdapter.setSelectedColor(it)
        })

        productViewModel.selectedSize.observe(viewLifecycleOwner, Observer {
            sizeAdapter.setSelectedSize(it)
        })

        productViewModel.images.observe(viewLifecycleOwner, Observer {
            Timber.e("Images = ${it?.size}")

            bannerAdapter.setItems(it)
            indicatorAdapter.setItems(it)
        })

        productViewModel.isFavorite().observe(viewLifecycleOwner, Observer {
            binding.favorite.isChecked = it
        })

        productViewModel.relativeProducts.observe(viewLifecycleOwner, Observer {
            relativeProductAdapter.setItems(it)
        })

        productViewModel.shouldShowSizeChart.observe(viewLifecycleOwner, Observer {
            tvSizeTable.visibility = if (it) View.VISIBLE else View.INVISIBLE
        })

        navController.getNavigationResult<Event<Int>>(IMAGE_POSITION)?.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { pos ->
                indicatorAdapter.setSelected(pos)
                banners.scrollToPosition(pos)
            }
        })
    }

    private fun initViews() = with(binding) {
        viewModel = productViewModel
        executePendingBindings()
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val colorRowCount = displayMetrics.widthPixels / 48.toPx()

        back.setOnClickListener {
            navController.popBackStack()
        }

        banners.adapter = bannerAdapter
        indicators.adapter = indicatorAdapter

        if (banners.onFlingListener == null) {
            banners.attachSnapHelperWithListener(
                LinearSnapHelper(),
                SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL_STATE_IDLE,
                object : SnapOnScrollListener.OnSnapPositionChangeListener {
                    override fun onSnapPositionChange(snapPosition: Int) {
                        indicatorAdapter.setSelected(snapPosition)
                    }
                })
        }

        rvColors.layoutManager = GridLayoutManager(activity, colorRowCount)
        rvColors.adapter = colorAdapter
        sizes.adapter = sizeAdapter

        relativeProductAdapter = ProductHorizontalAdapter(object :
            ProductHorizontalAdapter.ClickListener {
            override fun onItemClick(item: Product) {
                navController.navigate(
                    ProductFragmentDirections.actionGlobalProductFragment(item.id)
                )
            }

            override fun onFavoriteClick(item: Product, position: Int) {
                productViewModel.seeAlsoFavoriteToggle(item.id).observe(viewLifecycleOwner, Observer { result ->
                    when (result.status) {
                        Status.LOADING -> showLoading()
                        Status.ERROR -> {
                            hideLoading()
                            processError(result.error)
                        }
                        Status.SUCCESS -> {
                            hideLoading()
                            item.isFavorite = !item.isFavorite
                            relativeProductAdapter.notifyItemChanged(position)
                        }
                    }
                })
            }
        })

        relativeProducts.hasFixedSize()
        relativeProducts.adapter = relativeProductAdapter
//            val relativeSnapHelper = LinearSnapHelper()
//            relativeSnapHelper.attachToRecyclerView(relativeProducts)

        /*recentlyProductAdapter =
            ProductHorizontalAdapter()
        recentlyProducts.hasFixedSize()
        recentlyProducts.adapter = recentlyProductAdapter*/
//            val recentlySnapHelper = LinearSnapHelper()
//            recentlySnapHelper.attachToRecyclerView(recentlyProducts)

        infoToggle.setOnCheckedChangeListener { _, b ->
            val visibility = if (b) View.VISIBLE else View.GONE
            info.visibility = visibility
            tvReferenceHint.visibility = visibility
            tvReference.visibility = visibility
        }

        compositionToggle.setOnCheckedChangeListener { _, b ->
            composition.visibility = if (b) View.VISIBLE else View.GONE
        }

        tvSizeTable.setOnClickListener {
            productViewModel.sizeChart.observe(viewLifecycleOwner, Observer {
                it ?: return@Observer
                val direction = ProductFragmentDirections.actionProductFragmentToSizeChartFragment(it)
                navController.navigate(direction)
            })
        }

        /*categoryBrand.setOnClickListener {
            navController.navigate(
                ProductFragmentDirections.actionGlobalProductsFragment(
                    0,
                    viewModel?.product?.value?.category,
                    viewModel?.product?.value?.id ?: 0
                )
            )

            //navController.navigate(ProductFragmentDirections.actionGlobalProductFragment())
        }
*/
        /*category.setOnClickListener {
            navController.navigate(
                ProductFragmentDirections.actionGlobalProductsFragment(
                    0,
                    viewModel?.product?.value?.category?.name
                )
            )
        }*/

        /*likeLayout.setOnClickListener {
            Timber.d("like clicked!")
            viewModel?.favoriteToggle()?.observe(viewLifecycleOwner, Observer { result ->
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
        }*/

        shareLayout.setOnClickListener {
            productViewModel.shareText.observe(viewLifecycleOwner, Observer {
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT, it)
                intent.type = "text/plain"
                startActivity(Intent.createChooser(intent, "Отправить"))
            })
        }

        favorite.setOnCheckedChangeListener { compoundButton, _ ->
            if (compoundButton.isPressed) {
                productViewModel.favoriteToggle().observe(viewLifecycleOwner, Observer { result ->
                    when (result.status) {
                        Status.LOADING -> showLoading()
                        Status.ERROR -> {
                            hideLoading()
                            processError(result.error)
                        }
                        Status.SUCCESS -> {
                            hideLoading()
                            Timber.e(result.data.toString())
                        }
                    }
                })
            }
        }

        addToCart.setOnClickListener {
            if (productViewModel.selectedSize.value == null) {
                sizes.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.shake))
            } else {
                productViewModel.addToCart().observe(viewLifecycleOwner, Observer { result ->
                    when (result.status) {
                        Status.LOADING -> showLoading()
                        Status.ERROR -> {
                            hideLoading()
                            processError(result.error)
                        }
                        Status.SUCCESS -> {
                            hideLoading()
                            Toast.makeText(activity, R.string.product_added_to_cart, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        }


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
            if (it.getContentIfNotHandled() == true) productViewModel.sendRequest()
        })
        navController.navigate(destinationID)
    }
}