package uz.mod.templatex.ui.signUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.databinding.SignUpFragmentBinding
import uz.mod.templatex.model.remote.network.Status
import uz.mod.templatex.ui.code.CodeFragmentDirections
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

        viewModel.response.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    Status.LOADING -> showLoading()
                    Status.ERROR -> {
                        hideLoading()
                        showError(result.error)
                    }
                    Status.SUCCESS -> {
                        hideLoading()
                        findNavController().navigate(
                            CodeFragmentDirections.actionGlobalCodeFragment(
                                viewModel.phone.value!!,
                                false
                            )
                        )
                    }
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
        }
    }
}
