package uz.uzmobile.templatex.ui.signIn

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.navigation.fragment.findNavController
import timber.log.Timber
import uz.uzmobile.templatex.R
import uz.uzmobile.templatex.databinding.SignInFragmentBinding
import uz.uzmobile.templatex.model.remote.network.Status
import uz.uzmobile.templatex.ui.parent.ParentFragment
import uz.uzmobile.templatex.utils.MaskWatcher
import uz.uzmobile.templatex.utils.PhoneFieldFocusChangeListener

class SignInFragment : ParentFragment() {

    val viewModel: SignInViewModel by viewModel()

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

        viewModel.responce.observe(viewLifecycleOwner, Observer {result ->
            when(result.status) {
                Status.LOADING -> showLoading()
                Status.ERROR -> {
                    hideLoading()
                    showError(result.error)
                }
                Status.SUCCESS -> {
                    hideLoading()
                    Timber.e(result.data.toString())
                    result.data?.let {
                        sharedViewModel.loggedIn()
                        findNavController().popBackStack()
                    }
                }
            }
        })
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@SignInFragment.viewModel
            executePendingBindings()

            phone.addTextChangedListener(MaskWatcher.phoneWatcher())

            phone.onFocusChangeListener = PhoneFieldFocusChangeListener()

            passwordToggle.setOnCheckedChangeListener { _, b ->
                val cursorPosition = password.selectionStart
                password.transformationMethod =
                    if (b) HideReturnsTransformationMethod.getInstance() else PasswordTransformationMethod.getInstance()
                password.setSelection(cursorPosition)
            }

            forgotPasswordButton.setOnClickListener {
                goRecoveryPage()
            }

        }
    }

    fun goRecoveryPage() {
        findNavController().navigate(R.id.action_signInFragment_to_recoveryPasswordFragment)
    }
}
