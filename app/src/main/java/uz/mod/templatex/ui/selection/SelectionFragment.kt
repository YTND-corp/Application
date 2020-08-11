package uz.mod.templatex.ui.selection

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.annotation.IdRes
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import uz.mod.templatex.R
import uz.mod.templatex.databinding.SelectionFragmentBinding
import uz.mod.templatex.model.remote.network.ApiError
import uz.mod.templatex.model.remote.network.Status
import uz.mod.templatex.ui.common.*
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.utils.Const
import uz.mod.templatex.utils.Event
import uz.mod.templatex.utils.extension.getNavigationResult
import uz.mod.templatex.utils.extension.lazyFast

class SelectionFragment : ParentFragment(), OurServiceClickEvent {

    private val navController by lazyFast { findNavController() }
    val viewModel: SelectionViewModel by viewModel()

    private val binding by lazy { SelectionFragmentBinding.inflate(layoutInflater) }

    private lateinit var pageAdapter: SelectionPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        viewModel.getHome()

        pageAdapter = SelectionPagerAdapter(childFragmentManager)
    }

    override fun onServiceItemClicked(id: Int) = when (id) {
        CALL_US -> startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:${Const.PHONE_NUMBER}")))
        ASK_QUESTION -> navController.navigate(R.id.askQuestionFragmentFromSelection)
        WRITE_TO_US -> navController.navigate(R.id.askQuestionFragmentFromSelection)
        DELIVERY_AND_SAMPLE -> navController.navigate(R.id.action_selectionFragment_to_catalog_graph)
        else -> toast("Unknown command")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.selection_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        navController.navigate(item.itemId)
        return true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
        viewModel = this@SelectionFragment.viewModel
        executePendingBindings()

        pager.offscreenPageLimit = 3
        pager.adapter = pageAdapter

        //pager.setSwipePagingEnabled(false)

        tabs.setupWithViewPager(pager)

    }

    private fun processError(error: ApiError?) = when (error?.code) {
        Const.API_NO_CONNECTION_STATUS_CODE -> navigateAndObserveResult(R.id.noInternetFragment)
        Const.API_SERVER_FAIL_STATUS_CODE -> navigateAndObserveResult(R.id.serverErrorDialogFragment)
        Const.API_NEW_VERSION_AVAILABLE_STATUS_CODE -> navController.navigate(R.id.newVersionAvailableFragmentDialog)
        else -> showError(error)
    }

    private fun navigateAndObserveResult(@IdRes destinationID: Int) {
        navController.getNavigationResult<Event<Boolean>>()?.observe(viewLifecycleOwner, Observer {
            if (it.getContentIfNotHandled() == true) viewModel.getHome()
        })
        navController.navigate(destinationID)
    }
}

