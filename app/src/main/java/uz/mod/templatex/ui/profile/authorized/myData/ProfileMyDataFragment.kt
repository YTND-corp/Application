package uz.mod.templatex.ui.profile.authorized.myData

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.R
import uz.mod.templatex.databinding.ProfileMyDataFragmentBinding
import uz.mod.templatex.model.remote.network.ApiError
import uz.mod.templatex.model.remote.network.Status
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.utils.Const
import uz.mod.templatex.utils.Event
import uz.mod.templatex.utils.extension.getNavigationResult
import uz.mod.templatex.utils.extension.lazyFast
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ProfileMyDataFragment : ParentFragment() {

    private val navController by lazyFast { findNavController() }
    private val profileViewModel: ProfileMyDataViewModel by viewModel()

    private val binding by lazy { ProfileMyDataFragmentBinding.inflate(layoutInflater) }
    private var birthday = 12
    private var birthdayMonth = 9 //begins from zero
    private var birthdayYear = 1998


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

        profileViewModel.response.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.LOADING -> showLoading()
                Status.ERROR -> {
                    hideLoading()
                    processError(result.error)
                    binding.root.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    hideLoading()
                    binding.apply {
                        name.setText(result?.data?.user?.printFullName())
                        phone.setText(result?.data?.user?.phone)
                        email.setText(result?.data?.user?.email)
                        result?.data?.user?.birthday?.let {
                            birthDate.setText(
                                LocalDate.parse(it, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                                    .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                            )
                        }
                        sNotification.isChecked = result?.data?.user?.notifications == true
                        sSubscription.isChecked = result?.data?.user?.subscriptions == true
                    }
                    profileViewModel.possibleGenders.value = result.data?.genders
                    result.data?.genders?.forEach { gender ->
                        if (result?.data?.user?.gender == gender.type) {
                            profileViewModel.gender.value = gender.name
                            return@forEach
                        }
                    }
                }
            }
        })

        profileViewModel.getUserInfo()
    }

    private fun initViews(): Unit = with(binding) {
        viewModel = profileViewModel

        birthDate.setOnClickListener {
            showDatePickerDialog()
        }

        saveButton.setOnClickListener {
            hideKeyboard()
            profileViewModel.updateUserInfo().observe(viewLifecycleOwner, Observer { result ->
                when (result.status) {
                    Status.LOADING -> showLoading()
                    Status.ERROR -> {
                        hideLoading()
                        processError(result.error)
                    }
                    Status.SUCCESS -> {
                        hideLoading()
                        profileViewModel.onUserInfoUpdateSuccess()
                        navController.popBackStack()
                    }
                }
            })
        }

        gender.setOnClickListener {
            showGenderPickerDialog()
        }

    }

    private fun showGenderPickerDialog() {
        profileViewModel.possibleGenders.value?.map { it.name }?.let { genders->
            AlertDialog.Builder(requireContext())
                .setItems(genders.toTypedArray()) { _, i ->
                    profileViewModel.gender.value = genders[i]
                }.show()
        }
    }

    private fun showDatePickerDialog() = context?.let {
        DatePickerDialog(
            it,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                birthdayYear = year
                birthdayMonth = month
                birthday = dayOfMonth

                binding.birthDate.setText(
                    LocalDate.of(year, month + 1, dayOfMonth)
                        .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                )
            }, birthdayYear, birthdayMonth, birthday
        ).show()
    }


    private fun processError(error: ApiError?) = when (error?.code) {
        Const.API_NO_CONNECTION_STATUS_CODE -> navigateAndObserveResult(R.id.noInternetFragment)
        Const.API_SERVER_FAIL_STATUS_CODE -> navigateAndObserveResult(R.id.serverErrorDialogFragment)
        Const.API_NEW_VERSION_AVAILABLE_STATUS_CODE -> navController.navigate(R.id.newVersionAvailableFragmentDialog)
        else -> showError(error)
    }


    private fun navigateAndObserveResult(@IdRes destinationID: Int) {
        navController.getNavigationResult<Event<Boolean>>()?.observe(viewLifecycleOwner, Observer {
            if (it.getContentIfNotHandled() == true) profileViewModel.getUserInfo()
        })
        navController.navigate(destinationID)
    }
}