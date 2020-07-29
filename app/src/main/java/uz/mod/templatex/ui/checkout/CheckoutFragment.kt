package uz.mod.templatex.ui.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.BuildConfig
import uz.mod.templatex.databinding.CheckoutFragmentBinding
import uz.mod.templatex.model.remote.network.Status
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.utils.Const.PHONE_CODE_DEFAULT
import uz.mod.templatex.utils.MaskWatcher

class CheckoutFragment : ParentFragment() {

    private val viewModel: CheckoutViewModel by viewModel()

    private val binding by lazy { CheckoutFragmentBinding.inflate(layoutInflater) }

    companion object {
        fun newInstance() = CheckoutFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.response.observe(this, Observer { result ->
            when (result.status) {
                Status.LOADING -> showLoading()
                Status.ERROR -> {
                    hideLoading()
                    showError(result.error)
                }
                Status.SUCCESS -> {
                    hideLoading()
                    result.data?.let {
                        if (sharedViewModel.isAuthenticated.value == true) {
                            findNavController().navigate(
                                CheckoutFragmentDirections.actionCheckoutFragmentToAddressFragment(
                                    it,
                                    viewModel.getPhone()
                                )
                            )
                        } else {
                            findNavController().navigate(
                                CheckoutFragmentDirections.actionCheckoutFragmentToCodeFragment(
                                    viewModel.getPhone(),
                                    true
                                )
                            )
                        }
                    }
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this@CheckoutFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onDetach() {
        hideKeyboard()
        super.onDetach()
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@CheckoutFragment.viewModel
            executePendingBindings()

            phone.addTextChangedListener(MaskWatcher.phoneWatcher())
            phone.setOnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    if (phone.text?.length ?: 0 < 5 && phone.text?.equals(PHONE_CODE_DEFAULT) == false) {
                        phone.setText(PHONE_CODE_DEFAULT)
                    }
                } else {
                    if (phone.text?.length ?: 0 <= 5) {
                        phone.setText("")
                    }
                }
            }

            if (BuildConfig.DEBUG) {
                name.setText("Someone")
                surname.setText("Someone")
                email.setText("example@gmail.com")
                phone.setText("+998765432100")
            }
        }
    }
}