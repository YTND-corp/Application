package uz.mod.templatex.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.R
import uz.mod.templatex.databinding.CategoryFragmentBinding
import uz.mod.templatex.model.remote.network.ApiError
import uz.mod.templatex.model.remote.network.Status
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.utils.Const
import uz.mod.templatex.utils.Event
import uz.mod.templatex.utils.extension.getNavigationResult
import uz.mod.templatex.utils.extension.inputMethodManager
import uz.mod.templatex.utils.extension.lazyFast

class CategoryFragment : ParentFragment() {


    private val navController by lazyFast { findNavController() }
    val viewModel: CategoryViewModel by viewModel()

    private val binding by lazy { CategoryFragmentBinding.inflate(layoutInflater) }

    private lateinit var pageAdapter: CategoryPagerAdapter

    companion object {
        fun newInstance() = CategoryFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getCatalogs()

        pageAdapter = CategoryPagerAdapter(childFragmentManager)
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
                Status.LOADING -> showLoading()
                Status.ERROR -> {
                    hideLoading()
                    processError(result.error)
                }
                Status.SUCCESS -> {
                    hideLoading()
                    pageAdapter.setItems(result.data)
                }
            }
        })
    }

    private fun initViews(): Unit = with(binding) {
        viewModel = this@CategoryFragment.viewModel
        executePendingBindings()

        pager.offscreenPageLimit = 3
        pager.adapter = pageAdapter

        tabs.setupWithViewPager(pager)

        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) = Unit

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) = Unit

            override fun onPageSelected(position: Int) {
                requireActivity().inputMethodManager.hideSoftInputFromWindow(
                    view?.windowToken,
                    0
                )
            }
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
            if (it.getContentIfNotHandled() == true) viewModel.getCatalogs()
        })
        navController.navigate(destinationID)
    }
}