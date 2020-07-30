package uz.mod.templatex.ui.profile.authorized.myAddresses

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import uz.mod.templatex.R
import uz.mod.templatex.databinding.ProfileMyAddressesFragmentBinding
import uz.mod.templatex.model.remote.network.ApiError
import uz.mod.templatex.model.remote.network.Status
import uz.mod.templatex.ui.custom.LineDividerItemDecoration
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.ui.profile.authorized.myAddresses.ProfileMyAddressesAdapter.MyAddressesAdapterListener
import uz.mod.templatex.utils.Const
import uz.mod.templatex.utils.Event
import uz.mod.templatex.utils.extension.getNavigationResult
import uz.mod.templatex.utils.extension.lazyFast

class ProfileMyAddressesFragment : ParentFragment() {

    private val navController by lazyFast { findNavController() }
    val viewModel: ProfileMyAddressesViewModel by viewModel()

    private val binding by lazy { ProfileMyAddressesFragmentBinding.inflate(layoutInflater) }

    lateinit var myAddressesAdapterListener: MyAddressesAdapterListener
    lateinit var adapter: ProfileMyAddressesAdapter

    enum class Mode {
        CREATE,
        EDIT
    }

    companion object {
        fun newInstance() = ProfileMyAddressesFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        myAddressesAdapterListener = object : MyAddressesAdapterListener {
            override fun toEditMyAddress(addressId: Int) {
                viewModel.canEditAddress(addressId).observe(viewLifecycleOwner, Observer { result ->
                    when (result.status) {
                        Status.LOADING -> showLoading()
                        Status.ERROR -> {
                            hideLoading()
                            processError(result.error)
                        }
                        Status.SUCCESS -> {
                            hideLoading()
                            val action =
                                ProfileMyAddressesFragmentDirections.actionProfileMyAddressesFragmentToProfileMyAddressCreateEditFragment(
                                    Mode.EDIT,
                                    addressId
                                )
                            navController.navigate(action)
                        }
                    }
                })
            }
        }
        adapter = ProfileMyAddressesAdapter(myAddressesAdapterListener)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.profile_my_addresses_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.profileMyAddressCreateFragment -> {
                val action =
                    ProfileMyAddressesFragmentDirections.actionProfileMyAddressesFragmentToProfileMyAddressCreateEditFragment(
                        Mode.CREATE
                    )
                findNavController().navigate(action)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this@ProfileMyAddressesFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

        viewModel.response.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.LOADING -> showLoading()
                Status.ERROR -> {
                    hideLoading()
                    processError(result.error)
                }
                Status.SUCCESS -> {
                    hideLoading()
                    adapter.setItems(result.data)
                }
            }
        })

        viewModel.isEmpty.observe(viewLifecycleOwner, Observer { result ->
            Timber.e("IsEmpty = $result")
            binding.group.visibility = if (result == false) View.GONE else View.VISIBLE
        })

        viewModel.getMyAddresses()
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@ProfileMyAddressesFragment.viewModel
            executePendingBindings()

            rvAddresses.addItemDecoration(
                LineDividerItemDecoration(
                    requireContext(),
                    R.drawable.simple_list_divider
                )
            )
            rvAddresses.adapter = adapter
        }
    }

    private fun processError(error: ApiError?) {
        when (error?.code) {
            Const.API_NO_CONNECTION_STATUS_CODE -> {
                navController.getNavigationResult<Event<Boolean>>()?.observe(viewLifecycleOwner, Observer {
                    if (it.getContentIfNotHandled() == true) viewModel.getMyAddresses()
                })
                navController.navigate(R.id.noInternetFragment)
            }
            Const.API_SERVER_FAIL_STATUS_CODE -> {
                navController.getNavigationResult<Event<Boolean>>()?.observe(viewLifecycleOwner, Observer {
                    if (it.getContentIfNotHandled() == true) viewModel.getMyAddresses()
                })
                navController.navigate(R.id.serverErrorDialogFragment)
            }
            Const.API_NEW_VERSION_AVAILABLE_STATUS_CODE -> navController.navigate(R.id.newVersionAvailableFragmentDialog)
            else -> showError(error)
        }
    }
}