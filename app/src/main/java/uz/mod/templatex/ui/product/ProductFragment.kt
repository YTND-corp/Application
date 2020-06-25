package uz.mod.templatex.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearSnapHelper
import uz.mod.templatex.databinding.ProductFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import uz.mod.templatex.R
import uz.mod.templatex.utils.extension.attachSnapHelperWithListener
import uz.mod.templatex.model.remote.network.Status
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.utils.SnapOnScrollListener

class ProductFragment : ParentFragment() {

    val viewModel: ProductViewModel by viewModel()

    private val binding by lazy { ProductFragmentBinding.inflate(layoutInflater) }

    private val args: ProductFragmentArgs by navArgs()

    private var bannerAdapter = ProductBannerAdapter()
    private var indicatorAdapter = ProductBannerIndicatorAdapter()
    private var colorAdapter = ProductColorAdapter()
    private var sizeAdapter =  ProductSizeAdapter()
    private lateinit var relativeProductAdapter: ProductHorizontalAdapter
    private lateinit var recentlyProductAdapter: ProductHorizontalAdapter

    companion object {
        fun newInstance() = ProductFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setArgs(args)

        viewModel.images.observe(this, Observer {
            bannerAdapter.setItems(it)
            indicatorAdapter.setItems(it)
        })

        viewModel.colors.observe(this, Observer {
            viewModel.selectedColor.value = it?.firstOrNull()
            colorAdapter.setItems(it)
        })

        viewModel.sizes.observe(this, Observer {
            viewModel.selectedSize.value = it?.firstOrNull()
            sizeAdapter.setItems(it)
        })

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


        viewModel.response.observe(viewLifecycleOwner, Observer { result ->
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

    private fun initViews() {
        binding.apply {
            viewModel = this@ProductFragment.viewModel
            executePendingBindings()

            back.setOnClickListener {
                findNavController().popBackStack()
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

            colors.adapter = colorAdapter
            sizes.adapter = sizeAdapter

            relativeProductAdapter = ProductHorizontalAdapter(arrayListOf("1", "2", "3", "4", "5", "6", "7"))
            relativeProducts.hasFixedSize()
            relativeProducts.adapter = relativeProductAdapter
//            val relativeSnapHelper = LinearSnapHelper()
//            relativeSnapHelper.attachToRecyclerView(relativeProducts)

            recentlyProductAdapter =
                ProductHorizontalAdapter(arrayListOf("1", "2", "3", "4", "5", "6", "7"))
            recentlyProducts.hasFixedSize()
            recentlyProducts.adapter = recentlyProductAdapter
//            val recentlySnapHelper = LinearSnapHelper()
//            recentlySnapHelper.attachToRecyclerView(recentlyProducts)

            infoToggle.setOnCheckedChangeListener { _, b ->
                info.visibility = if (b) View.VISIBLE else View.GONE
            }

            compositionToggle.setOnCheckedChangeListener { _, b ->
                composition.visibility = if (b) View.VISIBLE else View.GONE
            }

            viewModel?.product?.observe(viewLifecycleOwner, Observer {
                brand.text = getString(R.string.all_the_brand, it?.brand?.name)
                categoryBrand.text = getString(R.string.all_the_category_of_the_brand,it?.category?.name ,it?.brand?.name)
                category.text = getString(R.string.all_the_brand, it?.category?.name)
            })

            categoryBrand.setOnClickListener {
                findNavController().navigate(
                    ProductFragmentDirections.actionGlobalProductsFragment(
                        args.categoryId,
                        viewModel?.product?.value?.category?.name,
                        viewModel?.product?.value?.brand?.id?:0
                    )
                )
            }

            category.setOnClickListener {
                findNavController().navigate(
                    ProductFragmentDirections.actionGlobalProductsFragment(
                        args.categoryId,
                        viewModel?.product?.value?.category?.name
                    )
                )
            }

            addToCart.setOnClickListener {
                viewModel?.addToCart()?.observe(viewLifecycleOwner, Observer { result ->
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
        }
    }
}