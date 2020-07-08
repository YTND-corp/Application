package uz.mod.templatex.ui.profile.authorized.myAddresses.createEdit

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.R
import uz.mod.templatex.databinding.ProfileCreateEditAddressFragmentBinding
import uz.mod.templatex.model.remote.network.Status
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.ui.profile.authorized.myAddresses.ProfileMyAddressesFragment

class ProfileMyAddressCreateEditFragment : ParentFragment() {

    val viewModel: ProfileMyAddressCreateEditViewModel by viewModel()
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
        binding.lifecycleOwner = this@ProfileMyAddressCreateEditFragment
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.profile_my_address_create_edit_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_exit -> {
                findNavController().popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

        viewModel.getCreateInfo().observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.LOADING -> showLoading()
                Status.ERROR -> {
                    hideLoading()
                    showError(result.error)
                }
                Status.SUCCESS -> {
                    hideLoading()
                    adapter.setItems(result.data)
                    result.data?.forEachIndexed { index, region ->
                        if (viewModel.region == region.name) {
                            binding.region.setSelection(index)
                            return@forEachIndexed
                        }
                    }
                }
            }
        })

        viewModel.response.observe(viewLifecycleOwner, Observer { result ->
            viewModel.region = result.region
            binding.apply {
                receiverName.setText(result.getFullName())
                address.setText(result.getStreetBuildingEntry())
                city.setText(result.city)
                zipCode.setText(result.postcode)
                phone.setText(result.phone)
            }
        })

        viewModel.responseStore.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
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

        viewModel.responseUpdate.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
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

        viewModel.setArgs(args)

        when (args.mode) {
            ProfileMyAddressesFragment.Mode.EDIT -> sharedViewModel.setTitle(getString(R.string.profile_my_addresses_edit_title))
            ProfileMyAddressesFragment.Mode.CREATE -> sharedViewModel.setTitle(getString(R.string.profile_my_addresses_create_title))
        }
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@ProfileMyAddressCreateEditFragment.viewModel
            executePendingBindings()

            saveButton.setOnClickListener {
                hideKeyboard()
                viewModel?.save()
            }

            region.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel?.selectedRegionId = adapter.getRegion(position).id
                }
            }

            region.adapter = adapter
        }
    }
}