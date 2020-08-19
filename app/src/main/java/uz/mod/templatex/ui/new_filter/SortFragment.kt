package uz.mod.templatex.ui.new_filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_main_filter.applyBt
import kotlinx.android.synthetic.main.sort_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.R
import uz.mod.templatex.ui.custom.LineDividerItemDecoration
import uz.mod.templatex.ui.new_filter.adapter.MainFilterAdapter
import uz.mod.templatex.ui.parent.ParentFragment

class SortFragment : ParentFragment() {

    private val sharedFilterViewModel: SharedFilterViewModel by activityViewModels()
    private val sortViewModel: SortViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sort_fragment, container, false)
    }

    override fun getLayoutID(): Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sortViewModel.sharedModel = sharedFilterViewModel
        val mainFilterAdapter = MainFilterAdapter(sharedFilterViewModel = sharedFilterViewModel)
        rvSort.adapter = mainFilterAdapter
        rvSort.layoutManager = LinearLayoutManager(context)
        rvSort.addItemDecoration(
            LineDividerItemDecoration(
                requireContext(),
                R.drawable.list_divider
            )
        )
        sortViewModel.itemsData.observe(viewLifecycleOwner, Observer {
            mainFilterAdapter.items.clear()
            mainFilterAdapter.items.addAll(it)
            mainFilterAdapter.notifyDataSetChanged()
        })

        sortViewModel.getSortList()

        applyBt.setOnClickListener {
            sharedFilterViewModel.applySort()
            findNavController().popBackStack()
        }
    }
}