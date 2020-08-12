package uz.mod.templatex.ui.profile.authorized.myAddresses.createEdit

import android.os.Bundle
import android.view.*
import androidx.annotation.IdRes
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.R
import uz.mod.templatex.databinding.ProfileCreateEditAddressFragmentBinding
import uz.mod.templatex.model.remote.network.ApiError
import uz.mod.templatex.model.remote.network.Status
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.ui.profile.authorized.myAddresses.ProfileMyAddressesFragment
import uz.mod.templatex.utils.Const
import uz.mod.templatex.utils.Event
import uz.mod.templatex.utils.extension.getNavigationResult
import uz.mod.templatex.utils.extension.lazyFast

class ProfileMyAddressCreateEditFragment : ParentFragment() {

    private val navController by lazyFast { findNavController() }
    private val profileAddressViewModel: ProfileMyAddressCreateEditViewModel by viewModel()
    val args: ProfileMyAddressCreateEditFragmentArgs by navArgs()

    private val binding by lazy { ProfileCreateEditAddressFragmentBinding.inflate(layoutInflater) }
    lateinit var adapter: RegionAdapter

    companion object {
        fun newInstance() = ProfileMyAddressCreateEditFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        adapter = RegionAdapter(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.profile_my_address_create_edit_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_exit -> {
                navController.popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

        profileAddressViewModel.getCreateInfo().observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.LOADING -> showLoading()
                Status.ERROR -> {
                    hideLoading()
                    processError(result.error)
                }
                Status.SUCCESS -> {
                    hideLoading()
                    profileAddressViewModel.allRegions.value = result.data
                    if (ProfileMyAddressesFragment.Mode.CREATE == args.mode) result.data?.forEach { region ->
                        if (region.name == getString(R.string.profile_my_address_default_city)) {
                            profileAddressViewModel.city.value = region.name
                            return@forEach
                        }
                    }
                    else result.data?.forEach { region ->
                        if (profileAddressViewModel.region == region.name) {
                            profileAddressViewModel.city.value = region.name
                            return@forEach
                        }
                    }

                }
            }
        })

        profileAddressViewModel.response.observe(viewLifecycleOwner, Observer { result ->
            profileAddressViewModel.region = result.region
            with(binding) {
                receiverName.setText(result.getFullName())
                address.setText(result.getStreetBuildingEntry())
                city.setText(result.city)
                zipCode.setText(result.postcode)
                phone.setText(result.phone)
            }
        })

        profileAddressViewModel.responseStore.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.LOADING -> showLoading()
                Status.ERROR -> {
                    hideLoading()
                    processError(result.error)
                }
                Status.SUCCESS -> {
                    hideLoading()
                    navController.popBackStack()
                }
            }
        })

        profileAddressViewModel.responseUpdate.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.LOADING -> showLoading()
                Status.ERROR -> {
                    hideLoading()
                    processError(result.error)
                }
                Status.SUCCESS -> {
                    hideLoading()
                    navController.popBackStack()
                }
            }
        })

        profileAddressViewModel.setArgs(args)

        when (args.mode) {
            ProfileMyAddressesFragment.Mode.EDIT -> sharedViewModel.setTitle(getString(R.string.profile_my_addresses_edit_title))
            ProfileMyAddressesFragment.Mode.CREATE -> sharedViewModel.setTitle(getString(R.string.profile_my_addresses_create_title))
        }
    }

    private fun initViews(): Unit = with(binding) {
        viewModel = profileAddressViewModel
        executePendingBindings()

        saveButton.setOnClickListener {
            hideKeyboard()
            viewModel?.save()
        }

        city.setOnClickListener {
            showCitySelectionDialog()
        }
    }

    private fun showCitySelectionDialog() {
        profileAddressViewModel.allRegions.value?.map { it.name }?.let { cityNames ->
            AlertDialog.Builder(requireContext())
                .setItems(cityNames.toTypedArray()) { _, i ->
                    profileAddressViewModel.city.value = cityNames[i]
                }.show()
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
            if (it.getContentIfNotHandled() == true) profileAddressViewModel.sendRequest()
        })
        navController.navigate(destinationID)
    }
}