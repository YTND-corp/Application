package uz.uzmobile.templatex.ui.recoveryPassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.androidx.viewmodel.ext.android.viewModel

import uz.uzmobile.templatex.databinding.RecoveryPasswordFragmentBinding

class RecoveryPasswordFragment : Fragment() {

    val viewModel: RecoveryPasswordViewModel by viewModel()

    private val binding by lazy { RecoveryPasswordFragmentBinding.inflate(layoutInflater) }

    companion object {
        fun newInstance() = RecoveryPasswordFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this@RecoveryPasswordFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@RecoveryPasswordFragment.viewModel
            executePendingBindings()
        }
    }
}
