package uz.mod.templatex.ui.checkout

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.checkout_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
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
    private val args: CheckoutFragmentArgs by navArgs()

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
                        if (it.confirmation == false && sharedViewModel.isAuthenticated.value == true) {
                            sharedViewModel.prefs.phone = phone.text.toString()
                            navController.navigate(
                                CheckoutFragmentDirections.actionCheckoutFragmentToAddressFragment(
                                    args.cartResponse,
                                    it,
                                    viewModel.getPhone()
                                )
                            )
                        } else {
                            navController.navigate(
                                CheckoutFragmentDirections.actionCheckoutFragmentToCodeFragment(
                                    args.cartResponse,
                                    viewModel.getPhone(),
                                    true
                                )
                            )
                        }
                    }
                }
            }
        })

        if (sharedViewModel.isAuthenticated.value == true) {
            viewModel.getUserInfo().observe(this, Observer { result ->
                when (result.status) {
                    Status.LOADING -> showLoading()
                    Status.ERROR -> {
                        hideLoading()
                        processError(result.error)
                    }
                    Status.SUCCESS -> {
                        hideLoading()
                        Timber.e(result.data.toString())
                        result.data?.user?.let {
                            binding.name.setText(it.firstName)
                            binding.surname.setText(it.lastName)
                            binding.email.setText(it.email)
                            binding.phone.setText(it.phone)
                        }
                    }
                }
            })
        }
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

    private fun initViews(): Unit = with(binding) {
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
    }

    private fun processError(error: ApiError?) {
        when (error?.code) {
            Const.API_NO_CONNECTION_STATUS_CODE -> navigateAndObserveResult(R.id.noInternetFragment)
            Const.API_SERVER_FAIL_STATUS_CODE -> navigateAndObserveResult(R.id.serverErrorDialogFragment)
            Const.API_NEW_VERSION_AVAILABLE_STATUS_CODE -> navController.navigate(R.id.newVersionAvailableFragmentDialog)
            else -> showError(error)
        }
    }

    private fun navigateAndObserveResult(@IdRes destinationID: Int) {
        navController.getNavigationResult<Event<Boolean>>()?.observe(viewLifecycleOwner, Observer {
            if (it.getContentIfNotHandled() == true) viewModel.user()
        })
        navController.navigate(destinationID)
    }
}