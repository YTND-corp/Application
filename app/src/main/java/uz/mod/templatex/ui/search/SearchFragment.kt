package uz.mod.templatex.ui.search

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.R
import uz.mod.templatex.databinding.SearchFragmentBinding
import uz.mod.templatex.ui.products.ProductAdapter
import uz.mod.templatex.utils.SimpleTextWatcher

class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModel()

    private val binding by lazy { SearchFragmentBinding.inflate(layoutInflater) }

    private lateinit var adapter: ProductAdapter

    companion object {
        fun newInstance() = SearchFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().navigate(item.itemId)
        return true
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
        observeViews()
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@SearchFragment.viewModel
            executePendingBindings()

            adapter = ProductAdapter { id, isFavorite ->
                // TODO adapter implementation
            }
            products.hasFixedSize()
            products.adapter = adapter

            searchEt.addTextChangedListener(SimpleTextWatcher {
                if (it.isNullOrEmpty()) this@SearchFragment.viewModel.isQuery.postValue(false)
            })
            searchEt.setOnEditorActionListener { v, actionId, event ->
                if ((event != null && (event.keyCode == KeyEvent.KEYCODE_ENTER)) || actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val searchQuery = searchEt.text.toString()
                    this@SearchFragment.viewModel.search(searchQuery)
                    // TODO searchLabelTv transform to horizontal recyclerView with multiple labels
                    true
                } else {
                    false
                }
            }
        }
    }

    private fun observeViews() {
        viewModel.isQuery.observe(viewLifecycleOwner, Observer {
            binding.searchLabelTv.isVisible = it
        })
    }
}