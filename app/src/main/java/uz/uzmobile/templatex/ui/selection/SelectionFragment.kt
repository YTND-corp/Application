package uz.uzmobile.templatex.ui.selection

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.uzmobile.templatex.R
import uz.uzmobile.templatex.databinding.SelectionFragmentBinding

class SelectionFragment: Fragment() {

    val viewModel: SelectionViewModel by viewModel()

    private val binding  by lazy { SelectionFragmentBinding.inflate(layoutInflater) }

    companion object {
        fun newInstance() = SelectionFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.selection_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.searchFragment -> true;
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding.lifecycleOwner = this@SelectionFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }




    private fun initViews() {
        binding.apply {
            viewModel = this@SelectionFragment.viewModel
            executePendingBindings()

            pager.adapter = SelectionPagerAdapter(
                childFragmentManager,
                resources.getStringArray(R.array.catalog_tab_names)
            )

            tabs.setupWithViewPager(pager)
        }
    }
}

