package uz.mod.templatex.ui.profile.authorized.myOrders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.databinding.ProfileMyOrdersFragmentBinding
import uz.mod.templatex.ui.parent.ParentFragment

class ProfileMyOrdersFragment : ParentFragment() {

    val viewModel: ProfileMyOrdersViewModel by viewModel()

    private val binding by lazy { ProfileMyOrdersFragmentBinding.inflate(layoutInflater) }

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

            if (!this@ProfileMyOrdersFragment.viewModel.getOrders()) {
                myOrdersNoOrdersContainer.isVisible = true
                myOrdersOrdersContainer.isVisible = false
            }
        }
    }
}