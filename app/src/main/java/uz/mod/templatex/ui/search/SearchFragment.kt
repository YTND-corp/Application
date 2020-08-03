package uz.mod.templatex.ui.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.search_fragment.*
import kotlinx.android.synthetic.main.view_search.*
import kotlinx.android.synthetic.main.view_search.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.R
import uz.mod.templatex.databinding.SearchFragmentBinding
import uz.mod.templatex.model.remote.network.ApiError
import uz.mod.templatex.model.remote.network.Status
import uz.mod.templatex.ui.custom.LineDividerItemDecoration
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.ui.product.ProductFragmentDirections
import uz.mod.templatex.utils.Const
import uz.mod.templatex.utils.Event
import uz.mod.templatex.utils.SimpleTextWatcher
import uz.mod.templatex.utils.extension.getNavigationResult
import uz.mod.templatex.utils.extension.lazyFast
import uz.mod.templatex.utils.extension.makeClearableEditText

class SearchFragment : ParentFragment() {

    private val viewModel: SearchViewModel by viewModel()

    private val navController by lazyFast { findNavController() }
    private val binding by lazy { SearchFragmentBinding.inflate(layoutInflater) }

    private lateinit var adapter: SearchAdapter

    companion object {
        fun newInstance() = SearchFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this@SearchFragment
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
                    adapter.setItems(result.data?.products)
                    placeholder_view.visibility = if (result.data?.products.isNullOrEmpty()) View.VISIBLE else View.GONE
                }
            }
        })

        viewModel.shouldClearResult.observe(viewLifecycleOwner, Observer {
            adapter.setItems(emptyList())
        })
    }

    override fun onDetach() {
        hideKeyboard()
        super.onDetach()
    }

    private fun initViews(): Unit = with(binding) {
        viewModel = this@SearchFragment.viewModel
        executePendingBindings()

        adapter = SearchAdapter { product ->
            navController.navigate(ProductFragmentDirections.actionGlobalProductFragment(product.id))
        }
        products.addItemDecoration(
            LineDividerItemDecoration(
                requireContext(),
                R.drawable.divider
            )
        )
        products.hasFixedSize()
        products.adapter = adapter

        addRightCancelDrawable(searchEt)
        searchEt.makeClearableEditText(null, null)
        searchContainer.searchEt.hint = getString(R.string.hint_catalog_search)
        searchContainer.searchEt.setOnEditorActionListener { v, actionId, event ->
            if ((event != null && (event.keyCode == KeyEvent.KEYCODE_ENTER)) || actionId == EditorInfo.IME_ACTION_SEARCH) {
                val searchQuery = searchContainer.searchEt.text.toString()
                this@SearchFragment.viewModel.search(searchQuery)
                hideKeyboard()
                true
            } else {
                false
            }
        }

        searchContainer.searchEt.addTextChangedListener(SimpleTextWatcher {
            viewModel?.search(searchContainer.searchEt.text.toString())
        })
    }

    private fun addRightCancelDrawable(editText: EditText) {
        context?.let {
            val cancel = ContextCompat.getDrawable(it, R.drawable.ic_cancel)
            cancel?.setBounds(0, 0, cancel.intrinsicWidth, cancel.intrinsicHeight)
            editText.setCompoundDrawables(null, null, cancel, null)
        }
    }

    private fun processError(error: ApiError?) {
        when (error?.code) {
            Const.API_NO_CONNECTION_STATUS_CODE -> {
                navController.getNavigationResult<Event<Boolean>>()?.observe(viewLifecycleOwner, Observer {
                    if (it.getContentIfNotHandled() == true) viewModel.search(viewModel.query.value ?: "")
                })
                navController.navigate(R.id.noInternetFragment)
            }
            Const.API_SERVER_FAIL_STATUS_CODE -> {
                navController.getNavigationResult<Event<Boolean>>()?.observe(viewLifecycleOwner, Observer {
                    if (it.getContentIfNotHandled() == true) viewModel.search(viewModel.query.value ?: "")
                })
                navController.navigate(R.id.serverErrorDialogFragment)
            }
            Const.API_NEW_VERSION_AVAILABLE_STATUS_CODE -> navController.navigate(R.id.newVersionAvailableFragmentDialog)
            else -> showError(error)
        }
    }
}