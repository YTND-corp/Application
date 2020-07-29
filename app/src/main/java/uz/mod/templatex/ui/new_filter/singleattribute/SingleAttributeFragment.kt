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
import kotlinx.android.synthetic.main.view_search.*
import uz.mod.templatex.R
import uz.mod.templatex.ui.custom.LineDividerItemDecoration
import uz.mod.templatex.ui.new_filter.SharedFilterViewModel
import uz.mod.templatex.ui.new_filter.adapter.SingleAttributeFilterAdapter
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.utils.extension.lazyFast

class SingleAttributeFragment : ParentFragment() {
    private val navController by lazyFast { findNavController() }
    private val sharedFilterViewModel: SharedFilterViewModel by activityViewModels()
    private val singleAttributeViewModel: SingleAttributeViewModel by viewModels()
    private val attrs: SingleAttributeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        singleAttributeViewModel.sharedModel = sharedFilterViewModel
        sharedFilterViewModel.buildTemporaryData()
        val adapter = SingleAttributeFilterAdapter {
            singleAttributeViewModel.onItemClick(it)
        }
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
        singleAttributeViewModel.isResetButtonVisible.observe(viewLifecycleOwner, Observer {
            btnReset.visibility = if (it) View.VISIBLE else View.GONE
        })
        singleAttributeViewModel.updateList.observe(viewLifecycleOwner, Observer {
            adapter.notifyDataSetChanged()
        })
        singleAttributeViewModel.attributeId = attrs.attrId
        singleAttributeViewModel.shouldShowResetButton()
        searchEt.hint = ""
        searchEt.addTextChangedListener { text -> singleAttributeViewModel.onQueryChanged(text.toString()) }
        applyBt.setOnClickListener {
            sharedFilterViewModel.saveTemporaryData()
            navController.popBackStack()
        }
        btnReset.setOnClickListener {
            singleAttributeViewModel.onResetClick()
        }
        back.setOnClickListener {
            navController.popBackStack()
        }
    }
}