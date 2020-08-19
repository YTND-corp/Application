package uz.mod.templatex.ui.subCategory

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import uz.mod.templatex.R
import uz.mod.templatex.databinding.SubCategoryFragmentBinding
import uz.mod.templatex.model.remote.network.ApiError
import uz.mod.templatex.ui.custom.LineDividerItemDecoration
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.utils.Const
import uz.mod.templatex.utils.Event
import uz.mod.templatex.utils.extension.getNavigationResult
import uz.mod.templatex.utils.extension.lazyFast

class SubCategoryFragment : ParentFragment() {

    private val navController by lazyFast { findNavController() }
    val viewModel: SubCategoryViewModel by viewModel()
    val args: SubCategoryFragmentArgs by navArgs()
    private lateinit var adapter: SubCategoryAdapter
    private val binding: SubCategoryFragmentBinding
        get() = childBinding as SubCategoryFragmentBinding

    companion object {
        fun newInstance() = SubCategoryFragment()
    }

    override fun getLayoutID(): Int? = R.layout.sub_category_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.e(args.category.toString())
        viewModel.setArgs(args)

        sharedViewModel.setTitle(args.category?.name)

        initViews()

        viewModel.subCategory.observe(viewLifecycleOwner, Observer {

            adapter.setItems(it)
        })
    }

    private fun initViews(): Unit = with(binding) {
        viewModel = this@SubCategoryFragment.viewModel
        executePendingBindings()

        adapter = SubCategoryAdapter()
        catalogDetails.adapter = adapter

        catalogDetails.addItemDecoration(
            LineDividerItemDecoration(
                requireContext(),
                R.drawable.list_divider
            )
        )

        continueButton.setOnClickListener {
            navController.navigate(
                SubCategoryFragmentDirections.actionGlobalProductsFragment(
                    args.category?.id ?: 0,
                    args.category?.name
                )
            )
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
            if (it.getContentIfNotHandled() == true) viewModel.getCatalogs()
        })
        navController.navigate(destinationID)
    }
}