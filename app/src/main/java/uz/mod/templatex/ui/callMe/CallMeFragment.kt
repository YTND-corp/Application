package uz.mod.templatex.ui.callMe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.databinding.CallMeFragmentBinding
import uz.mod.templatex.ui.parent.ParentFragment

class CallMeFragment : ParentFragment() {

    val viewModel: CallMeViewModel by viewModel()

    private val binding by lazy { CallMeFragmentBinding.inflate(layoutInflater) }

    companion object {
        fun newInstance() = CallMeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this@CallMeFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@CallMeFragment.viewModel
            executePendingBindings()
        }
    }
}