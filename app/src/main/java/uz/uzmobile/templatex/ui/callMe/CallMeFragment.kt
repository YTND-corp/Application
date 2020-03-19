package uz.uzmobile.templatex.ui.callMe

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import uz.uzmobile.templatex.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.uzmobile.templatex.databinding.CallMeFragmentBinding

class CallMeFragment : Fragment() {

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