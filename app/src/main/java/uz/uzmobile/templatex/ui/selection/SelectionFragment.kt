package uz.uzmobile.templatex.ui.selection

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import uz.uzmobile.templatex.R
import uz.uzmobile.templatex.databinding.SelectionFragmentBinding
import uz.uzmobile.templatex.model.remote.network.Status
import uz.uzmobile.templatex.ui.parent.ParentFragment

class SelectionFragment: ParentFragment() {

    val viewModel: SelectionViewModel by viewModel()

    private val binding  by lazy { SelectionFragmentBinding.inflate(layoutInflater) }

    private lateinit var pageAdapter: SelectionPagerAdapter

    companion object {
        fun newInstance() = SelectionFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);

        viewModel.getHome()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.selection_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().navigate(item.itemId)
        return true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
                    Timber.e(result.error.toString())
                    hideLoading()
                    showError(result.error)
                }
                Status.SUCCESS -> {
                    hideLoading()
                    Timber.e(result.data.toString())
                    pageAdapter.setItems(result.data)
                }
            }
        })
    }




    private fun initViews() {
        binding.apply {
            viewModel = this@SelectionFragment.viewModel
            executePendingBindings()

            pageAdapter = SelectionPagerAdapter(childFragmentManager)

            pager.offscreenPageLimit = 3
            pager.adapter = pageAdapter

            pager.setOnTouchListener { view, motionEvent ->
                return@setOnTouchListener true
            }

            pager.setSwipePagingEnabled(false)

            tabs.setupWithViewPager(pager)
        }
    }
}

