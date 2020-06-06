package uz.uzmobile.templatex.ui.search

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.uzmobile.templatex.R
import uz.uzmobile.templatex.databinding.SearchFragmentBinding
import uz.uzmobile.templatex.ui.products.ProductAdapter

class SearchFragment : Fragment() {

    val viewModel: SearchViewModel by viewModel()

    private val binding by lazy { SearchFragmentBinding.inflate(layoutInflater) }


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
        binding.apply {
            viewModel = this@SearchFragment.viewModel
            executePendingBindings()

            val adapter = ProductAdapter{ id, isFavorite ->

            }

            products.hasFixedSize()
            products.adapter = adapter

        }
    }
}