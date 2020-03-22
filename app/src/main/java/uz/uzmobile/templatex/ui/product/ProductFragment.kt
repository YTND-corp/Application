package uz.uzmobile.templatex.ui.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import uz.uzmobile.templatex.databinding.ProductFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductFragment : Fragment() {

    val viewModel: ProductViewModel by viewModel()

    private val binding by lazy { ProductFragmentBinding.inflate(layoutInflater) }

    private lateinit var bannerAdapter: ProductBannerAdapter
    private lateinit var colorAdapter: ProductColorAdapter
    private lateinit var sizeAdapter: ProductSizeAdapter
    private lateinit var relativeProductAdapter: ProductHorizontalAdapter
    private lateinit var recentlyProductAdapter: ProductHorizontalAdapter

    companion object {
        fun newInstance() = ProductFragment()
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
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@ProductFragment.viewModel
            executePendingBindings()

            back.setOnClickListener {
                findNavController().popBackStack()
            }


            bannerAdapter = ProductBannerAdapter(arrayListOf("1", "2", "3"))
            banners.hasFixedSize()
            banners.adapter = bannerAdapter
            indicator.setRecyclerView(banners)
            val snapHelper = LinearSnapHelper() // Or PagerSnapHelper
            snapHelper.attachToRecyclerView(banners)

            colorAdapter = ProductColorAdapter(arrayListOf("1", "2", "3", "4"))
            colors.hasFixedSize()
            colors.adapter = colorAdapter

            sizeAdapter = ProductSizeAdapter(arrayListOf("1", "2", "3", "4"))
            sizes.hasFixedSize()
            sizes.adapter = sizeAdapter

            relativeProductAdapter = ProductHorizontalAdapter(arrayListOf("1", "2", "3", "4"))
            relativeProducts.hasFixedSize()
            relativeProducts.adapter = relativeProductAdapter

            recentlyProductAdapter = ProductHorizontalAdapter(arrayListOf("1", "2", "3", "4"))
            recentlyProducts.hasFixedSize()
            recentlyProducts.adapter = recentlyProductAdapter

            infoToggle.setOnCheckedChangeListener { compoundButton, b ->
                info.visibility = if (b) View.VISIBLE else View.GONE
            }

            compositionToggle.setOnCheckedChangeListener { compoundButton, b ->
                composition.visibility = if (b) View.VISIBLE else View.GONE
            }
        }
    }
}