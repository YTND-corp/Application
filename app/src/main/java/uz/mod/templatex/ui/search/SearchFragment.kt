package uz.mod.templatex.ui.search

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.R
import uz.mod.templatex.databinding.SearchFragmentBinding
import uz.mod.templatex.ui.cart.CartAdapter
import uz.mod.templatex.ui.products.ProductAdapter

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
    }

    private fun initViews() {
        adapter = ProductAdapter { id, isFavorite ->
            // TODO
        }

        binding.apply {
            viewModel = this@SearchFragment.viewModel
            executePendingBindings()

            products.hasFixedSize()
            products.adapter = adapter

            searchEt.setOnEditorActionListener { v, actionId, event ->
                if ((event != null && (event.keyCode == KeyEvent.KEYCODE_ENTER)) || actionId == EditorInfo.IME_ACTION_SEARCH) {
                    this@SearchFragment.viewModel.search(searchEt.text.toString())
                    true
                } else {
                    false
                }
            }
        }
    }
}