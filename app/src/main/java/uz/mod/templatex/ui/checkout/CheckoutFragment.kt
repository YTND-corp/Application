package uz.mod.templatex.ui.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.BuildConfig
import uz.mod.templatex.R
import uz.mod.templatex.databinding.CheckoutFragmentBinding
import uz.mod.templatex.model.remote.network.ApiError
import uz.mod.templatex.model.remote.network.Status
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.utils.Const
import uz.mod.templatex.utils.Const.PHONE_CODE_DEFAULT
import uz.mod.templatex.utils.Event
import uz.mod.templatex.utils.MaskWatcher
import uz.mod.templatex.utils.extension.getNavigationResult
import uz.mod.templatex.utils.extension.lazyFast

class CheckoutFragment : ParentFragment() {

    private val navController by lazyFast { findNavController() }
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
                    processError(result.error)
                }
                Status.SUCCESS -> {
                    hideLoading()
                    result.data?.let {
                        if (sharedViewModel.isAuthenticated.value == true) {
                            navController.navigate(
                                CheckoutFragmentDirections.actionCheckoutFragmentToAddressFragment(
                                    it,
                                    viewModel.getPhone()
                                )
                            )
                        } else {
                            navController.navigate(
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
            phone.setOnFocusChangeListener { _, hasFocus ->
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

    private fun processError(error: ApiError?) {
        if (error?.code == Const.API_NO_CONNECTION_STATUS_CODE) {
            navController.getNavigationResult<Event<Boolean>>()?.observe(viewLifecycleOwner, Observer {
                if (it.getContentIfNotHandled() == true) viewModel.user()
            })
            navController.navigate(R.id.noInternetFragment)
        } else showError(error)
    }
}