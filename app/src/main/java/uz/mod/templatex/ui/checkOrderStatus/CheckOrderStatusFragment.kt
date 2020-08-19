package uz.mod.templatex.ui.checkOrderStatus

import android.os.Bundle
import android.view.View
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.R
import uz.mod.templatex.databinding.CheckOrderStatusFragmentBinding
import uz.mod.templatex.ui.parent.ParentFragment

class CheckOrderStatusFragment : ParentFragment() {

    val viewModel: CheckOrderStatusViewModel by viewModel()

    private val binding: CheckOrderStatusFragmentBinding
        get() = childBinding as CheckOrderStatusFragmentBinding

    companion object {
        fun newInstance() = CheckOrderStatusFragment()
    }

    override fun getLayoutID() = R.layout.check_order_status_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews(): Unit = with(binding) {
        viewModel = this@CheckOrderStatusFragment.viewModel
        executePendingBindings()
    }
}