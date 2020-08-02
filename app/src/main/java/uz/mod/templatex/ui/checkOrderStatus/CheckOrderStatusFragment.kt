package uz.mod.templatex.ui.checkOrderStatus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.androidx.viewmodel.ext.android.viewModel

import uz.mod.templatex.databinding.CheckOrderStatusFragmentBinding
import uz.mod.templatex.ui.parent.ParentFragment

class CheckOrderStatusFragment : ParentFragment() {

    val viewModel: CheckOrderStatusViewModel by viewModel()

    private val binding by lazy { CheckOrderStatusFragmentBinding.inflate(layoutInflater) }

    companion object {
        fun newInstance() = CheckOrderStatusFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this@CheckOrderStatusFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews(): Unit = with(binding) {
        viewModel = this@CheckOrderStatusFragment.viewModel
        executePendingBindings()
    }
}