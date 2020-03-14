package uz.uzmobile.templatex.ui.signUp

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import uz.uzmobile.templatex.databinding.SignUpFragmentBinding


class SignUpFragment : Fragment() {

    val viewModel: SignUpViewModel by viewModels()

    private val binding by lazy { SignUpFragmentBinding.inflate(layoutInflater) }

    companion object {
        fun newInstance() = SignUpFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this@SignUpFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@SignUpFragment.viewModel
            executePendingBindings()

            passwordToggle.setOnCheckedChangeListener { _, b ->
                val cursorPosition: Int = etPassword.selectionStart
                etPassword.transformationMethod =
                    if (b) HideReturnsTransformationMethod.getInstance() else PasswordTransformationMethod.getInstance()
                etPassword.setSelection(cursorPosition)
            }

            repeatPasswordToggle.setOnCheckedChangeListener { _, b ->
                val cursorPosition: Int = etRepeatPassword.selectionStart
                etRepeatPassword.transformationMethod =
                    if (b) HideReturnsTransformationMethod.getInstance() else PasswordTransformationMethod.getInstance()
                etRepeatPassword.setSelection(cursorPosition)
            }
        }
    }
}
