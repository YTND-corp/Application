package uz.mod.templatex.ui.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import uz.mod.templatex.R
import uz.mod.templatex.databinding.AddressFragmentBinding
import uz.mod.templatex.model.local.entity.Adres
import uz.mod.templatex.model.remote.network.Status
import uz.mod.templatex.ui.custom.LineDividerItemDecoration
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.utils.extension.lazyFast

class AddressFragment : ParentFragment() {


    private val navController by lazyFast { findNavController() }

    val viewModel: AddressViewModel by viewModel()

    private val binding by lazy { AddressFragmentBinding.inflate(layoutInflater) }

    val args: AddressFragmentArgs by navArgs()

    companion object {
        fun newInstance() = AddressFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setArgs(args)
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

        viewModel.response.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.LOADING -> showLoading()
                Status.ERROR -> {
                    hideLoading()
                    showError(result.error)
                }
                Status.SUCCESS -> {
                    hideLoading()
                    Timber.e(result.data.toString())
                }
            }
        })

        viewModel.cities.observe(viewLifecycleOwner, Observer { cities ->

            Timber.e(cities.toString())

            binding.city.setOnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    view.clearFocus()

                    val temp:  Array<String> = Array(cities?.size?:0) { cities?.get(it)?.name?:"" }
                    AlertDialog.Builder(requireContext())
                        .setItems(temp) { a, i ->
                            viewModel.city.value = cities?.get(i)
                        }
                        .show()
                }
            }
        })

        viewModel.getCities()
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@AddressFragment.viewModel
            executePendingBindings()


            addresses.adapter = AddressAdapter(listOf(Adres(0), Adres(0), Adres(0)))
            addresses.addItemDecoration(
                LineDividerItemDecoration(
                    requireContext(),
                    R.drawable.divider
                )
            )

            continueButton.setOnClickListener {
                if (args.response?.confirmation == false) {
                    navController.navigate(
                        AddressFragmentDirections.actionAddressFragmentToDeliveryFragment(
                            args.response,
                            viewModel?.getDetails()
                        )
                    )
                } else {
                    navController.navigate(
                        AddressFragmentDirections.actionAddressFragmentToDeliveryFragment(
                            args.response,
                            viewModel?.getDetails()
                        )
                    )
                }
            }
        }
    }
}
