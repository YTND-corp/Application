package uz.mod.templatex.ui.profile.authorized.myOrders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.view_search.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.R
import uz.mod.templatex.databinding.ProfileMyOrdersFragmentBinding
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.utils.SimpleTextWatcher

class ProfileMyOrdersFragment : ParentFragment() {

    val viewModel: ProfileMyOrdersViewModel by viewModel()

    private val binding by lazy { ProfileMyOrdersFragmentBinding.inflate(layoutInflater) }

    private val myOrdersAdapterListener = object : MyOrdersAdapterListener {
        override fun toMyOrder() {
            findNavController().navigate(R.id.action_profileMyOrdersFragment_to_profileMyOrderFragment)
        }
    }

    private var adapter = ProfileMyOrdersAdapter(myOrdersAdapterListener)

    companion object {
        fun newInstance() = ProfileMyOrdersFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this@ProfileMyOrdersFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@ProfileMyOrdersFragment.viewModel
            executePendingBindings()

            if (!this@ProfileMyOrdersFragment.viewModel.isOrdersAvailable()) {
                myOrdersNoOrdersContainer.isVisible = true
                myOrdersOrdersContainer.isVisible = false
            }

            myOrdersSearchContainer.searchEt.hint =
                getString(R.string.profile_my_orders_search_hint)
            myOrdersSearchContainer.searchEt.addTextChangedListener(SimpleTextWatcher {
                val searchQuery = myOrdersSearchContainer.searchEt.text.toString()
                this@ProfileMyOrdersFragment.viewModel.search(searchQuery)
            })

            myOrdersRv.adapter = adapter
            adapter.setItems(this@ProfileMyOrdersFragment.viewModel.getOrders())
        }
    }
}