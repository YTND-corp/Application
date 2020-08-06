package uz.mod.templatex.ui.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    //TODO delete DeliveryFragment after completing this
    private val navController by lazyFast { findNavController() }
    private val addressViewModel: AddressViewModel by viewModel()
    private val binding by lazy { AddressFragmentBinding.inflate(layoutInflater) }
    val args: AddressFragmentArgs by navArgs()
    private val adapter by lazyFast {
        AddressAdapter {
            Timber.e("${it.price}")
            addressViewModel.delivery.value = it
        }
    }

    companion object {
        fun newInstance() = AddressFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addressViewModel.setArgs(args)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this@AddressFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

        addressViewModel.response.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.LOADING -> showLoading()
                Status.ERROR -> {
                    hideLoading()
                    processError(result.error)
                }
                Status.SUCCESS -> {
                    hideLoading()
                    Timber.e(result.data.toString())
                }
            }
        })

        addressViewModel.cities.observe(viewLifecycleOwner, Observer { cities ->

            Timber.e(cities.toString())

            binding.city.setOnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    view.clearFocus()

                    val temp: Array<String> = Array(cities?.size ?: 0) { cities?.get(it)?.name ?: "" }
                    AlertDialog.Builder(requireContext())
                        .setItems(temp) { _, i ->
                            addressViewModel.city.value = cities?.get(i)
                        }
                        .show()
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
