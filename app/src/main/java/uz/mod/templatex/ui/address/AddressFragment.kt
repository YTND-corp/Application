package uz.mod.templatex.ui.address

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import uz.mod.templatex.R
import uz.mod.templatex.databinding.AddressFragmentBinding
import uz.mod.templatex.model.remote.network.ApiError
import uz.mod.templatex.model.remote.network.Status
import uz.mod.templatex.ui.custom.LineDividerItemDecoration
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.utils.Const
import uz.mod.templatex.utils.Event
import uz.mod.templatex.utils.extension.getNavigationResult
import uz.mod.templatex.utils.extension.lazyFast

class AddressFragment : ParentFragment() {

    private val navController by lazyFast { findNavController() }
    private val addressViewModel: AddressViewModel by viewModel()
    val args: AddressFragmentArgs by navArgs()
    private val adapter by lazyFast {
        AddressAdapter {
            addressViewModel.delivery.value = it
        }
    }

    private val binding : AddressFragmentBinding
        get() = childBinding as AddressFragmentBinding

    companion object {
        fun newInstance() = AddressFragment()
    }

    override fun getLayoutID() = R.layout.address_fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addressViewModel.setArgs(args)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()


        addressViewModel.userAddressesInServer.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.LOADING, Status.ERROR -> {
                }
                Status.SUCCESS -> {

                    addressViewModel.setupWithDefaultAddress(
                        result.data
                    )
                }
            }
        })

        addressViewModel.response.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.LOADING -> showLoading()
                Status.ERROR -> {
                    hideLoading()
                    processError(result.error)
                }
                Status.SUCCESS -> hideLoading()
            }
        })

        addressViewModel.cities.observe(viewLifecycleOwner, Observer { result ->
            result.getContentIfNotHandled()?.let { cities ->
                Timber.e(cities.toString())

                if (addressViewModel.city.value == null)
                    cities.find { it.id == Const.DEFAULT_USER_REGION_ID }?.let {
                        addressViewModel.city.value = it
                    }

                binding.city.setOnFocusChangeListener { view, hasFocus ->
                    if (hasFocus) {
                        hideKeyboard()
                        view.clearFocus()

                        val temp: Array<String> = Array(cities.size) { cities[it].name ?: "" }
                        AlertDialog.Builder(requireContext())
                            .setItems(temp) { _, i ->
                                addressViewModel.city.value = cities[i]
                            }.show()
                    }
                }
            }
        })

        addressViewModel.getCities()
    }



    private fun initViews(): Unit = with(binding) {
        viewModel = addressViewModel
        executePendingBindings()

        addresses.adapter = adapter
        adapter.setItems(args.response?.delivery)
        addresses.addItemDecoration(
            LineDividerItemDecoration(
                requireContext(),
                R.drawable.divider
            )
        )

        continueButton.setOnClickListener {
            navController.navigate(
                AddressFragmentDirections.actionAddressFragmentToPaymentFragment(
                    args.cartResponse,
                    args.response,
                    addressViewModel.getDetails()
                )
            )
        }
    }

    private fun processError(error: ApiError?) = when (error?.code) {
        Const.API_NO_CONNECTION_STATUS_CODE -> navigateAndObserveResult(R.id.noInternetFragment)
        Const.API_SERVER_FAIL_STATUS_CODE -> navigateAndObserveResult(R.id.serverErrorDialogFragment)
        Const.API_NEW_VERSION_AVAILABLE_STATUS_CODE -> navController.navigate(R.id.newVersionAvailableFragmentDialog)
        else -> showError(error)
    }

    private fun navigateAndObserveResult(@IdRes destinationID: Int) {
        navController.getNavigationResult<Event<Boolean>>()?.observe(viewLifecycleOwner, Observer {
            if (it.getContentIfNotHandled() == true) addressViewModel.getCities()
        })
        navController.navigate(destinationID)
    }
}
