package uz.mod.templatex.ui.new_filter.singleattribute

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_main_filter.*
import uz.mod.templatex.R
import uz.mod.templatex.ui.custom.LineDividerItemDecoration
import uz.mod.templatex.ui.new_filter.SharedFilterViewModel
import uz.mod.templatex.ui.new_filter.adapter.MainFilterAdapter
import uz.mod.templatex.ui.new_filter.adapter.SingleAttributeFilterAdapter
import uz.mod.templatex.ui.parent.ParentFragment

class SingleAttributeFragment : ParentFragment() {
    val sharedFilterViewModel : SharedFilterViewModel by activityViewModels<SharedFilterViewModel>()
    val singleAttributeViewModel : SingleAttributeViewModel by viewModels()
    val attrs : SingleAttributeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_filter,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        singleAttributeViewModel.sharedModel = sharedFilterViewModel
        sharedFilterViewModel.buildTemporaryData()
        val adapter = SingleAttributeFilterAdapter(filter = sharedFilterViewModel.cachedFilter,attrId = attrs.attrId)
        rvList.adapter = adapter
        rvList.layoutManager = LinearLayoutManager(context)
        rvList.addItemDecoration(
            LineDividerItemDecoration(
                requireContext(),
                R.drawable.list_divider
            )
        )
        singleAttributeViewModel.itemsData.observe(viewLifecycleOwner, Observer {
            adapter.items.clear()
            adapter.items.addAll(it)
            adapter.notifyDataSetChanged()
        })
        singleAttributeViewModel.attributeId = attrs.attrId
        filterEt.addTextChangedListener { text -> singleAttributeViewModel.onQueryChanged(text.toString()) }
        applyBt.setOnClickListener {
            sharedFilterViewModel.saveTemporaryData()
            findNavController().popBackStack()
        }
    }
}