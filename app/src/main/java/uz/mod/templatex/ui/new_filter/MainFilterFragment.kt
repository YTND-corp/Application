package uz.mod.templatex.ui.new_filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_main_filter.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.R
import uz.mod.templatex.ui.custom.LineDividerItemDecoration
import uz.mod.templatex.ui.new_filter.adapter.MainFilterAdapter
import uz.mod.templatex.ui.parent.ParentFragment

class MainFilterFragment : ParentFragment() {
    val sharedFilterViewModel : SharedFilterViewModel by activityViewModels<SharedFilterViewModel>()
    val mainFilterViewModel : MainFilterViewModel by viewModel()
    val args : MainFilterFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_filter,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filterLl.visibility = View.GONE
        mainFilterViewModel.sharedModel = sharedFilterViewModel
        val mainFilterAdapter = MainFilterAdapter(sharedFilterViewModel = sharedFilterViewModel)
        rvList.adapter = mainFilterAdapter
        rvList.layoutManager = LinearLayoutManager(context)
        rvList.addItemDecoration(
            LineDividerItemDecoration(
                requireContext(),
                R.drawable.list_divider
            )
        )
        mainFilterViewModel.itemsData.observe(viewLifecycleOwner, Observer {
            mainFilterAdapter.items.clear()
            mainFilterAdapter.items.addAll(it)
            mainFilterAdapter.notifyDataSetChanged()
        })
        mainFilterViewModel.categoryId = args.categoryId
    }
}