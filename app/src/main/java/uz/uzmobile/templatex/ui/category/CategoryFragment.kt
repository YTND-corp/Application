package uz.uzmobile.templatex.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import uz.uzmobile.templatex.databinding.CategoryFragmentBinding
import uz.uzmobile.templatex.model.remote.network.Status
import uz.uzmobile.templatex.ui.parent.ParentFragment

class CategoryFragment : ParentFragment() {

    val viewModel: CategoryViewModel by viewModel()

    private val binding by lazy { CategoryFragmentBinding.inflate(layoutInflater) }

    private lateinit var pageAdapter: CategoryPagerAdapter

    companion object {
        fun newInstance() = CategoryFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getCatalogs()
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

        viewModel.response.observe(viewLifecycleOwner, Observer {result ->
            when(result.status) {
                Status.LOADING -> showLoading()
                Status.ERROR -> {
                    hideLoading()
                    showError(result.error)
                }
                Status.SUCCESS -> {
                    hideLoading()
                    pageAdapter.setItems(result.data)
                }
            }
        })
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@CategoryFragment.viewModel
            executePendingBindings()


            pageAdapter = CategoryPagerAdapter(childFragmentManager)

            pager.offscreenPageLimit = 3
            pager.adapter = pageAdapter

            tabs.setupWithViewPager(pager)
        }
    }
}