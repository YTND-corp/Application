package uz.uzmobile.templatex.ui.signIn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import uz.uzmobile.templatex.R
import uz.uzmobile.templatex.databinding.SignInFragmentBinding

class SignInFragment : Fragment() {

    val viewModel: SignInViewModel by viewModels()

    private val binding by lazy { SignInFragmentBinding.inflate(layoutInflater) }

    companion object {
        fun newInstance() = SignInFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this@SignInFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@SignInFragment.viewModel
            executePendingBindings()

            forgotPasswordButton.setOnClickListener {
                goRecoveryPage()
            }
        }
    }

    fun goRecoveryPage() {
        findNavController().navigate(R.id.action_signInFragment_to_recoveryPasswordFragment)
    }
}
