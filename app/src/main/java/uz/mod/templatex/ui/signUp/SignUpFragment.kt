package uz.mod.templatex.ui.signUp

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.databinding.SignUpFragmentBinding
import uz.mod.templatex.model.remote.network.Status
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.utils.MaskWatcher


class SignUpFragment : ParentFragment() {

    val viewModel: SignUpViewModel by viewModel()

    private val binding by lazy { SignUpFragmentBinding.inflate(layoutInflater) }

    companion object {
        fun newInstance() = SignUpFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        viewModel.responce.observe(viewLifecycleOwner, Observer {result ->
            when(result.status) {
                Status.LOADING -> showLoading()
                Status.ERROR -> {
                    hideLoading()
                    showError(result.error)
                }
                Status.SUCCESS -> {
                    hideLoading()
                    findNavController().popBackStack()
                }
            }
        })

    }

    private fun initViews() {
        binding.apply {
            viewModel = this@SignUpFragment.viewModel
            executePendingBindings()

            phone.addTextChangedListener(MaskWatcher.phoneWatcher())

            phone.setOnFocusChangeListener { view, hasFocus ->

                if (hasFocus) {
                    if (phone.text?.length ?: 0 < 5 && phone.text?.equals("+998") == false) {
                        phone.setText("+998")
                    }
                } else {
                    if (phone.text?.length ?: 0 <= 5) {
                        phone.setText("")
                    }
                }
            }

            passwordToggle.setOnCheckedChangeListener { _, b ->
                val cursorPosition = password.selectionStart
                password.transformationMethod =
                    if (b) HideReturnsTransformationMethod.getInstance() else PasswordTransformationMethod.getInstance()
                password.setSelection(cursorPosition)
            }

            repeatPasswordToggle.setOnCheckedChangeListener { _, b ->
                val cursorPosition: Int = repeatPassword.selectionStart
                repeatPassword.transformationMethod =
                    if (b) HideReturnsTransformationMethod.getInstance() else PasswordTransformationMethod.getInstance()
                repeatPassword.setSelection(cursorPosition)
            }
        }
    }
}
