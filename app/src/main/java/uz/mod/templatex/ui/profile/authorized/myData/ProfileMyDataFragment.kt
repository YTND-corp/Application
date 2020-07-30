package uz.mod.templatex.ui.profile.authorized.myData

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
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
    val viewModel: ProfileMyDataViewModel by viewModel()

    private val binding by lazy { ProfileMyDataFragmentBinding.inflate(layoutInflater) }
    private lateinit var genderAdapter: GenderAdapter
    private var birthday = 12
    private var birthdayMonth = 9 //begins from zero
    private var birthdayYear = 1998

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        genderAdapter = GenderAdapter(context)
    }

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

        viewModel.response.observe(viewLifecycleOwner, Observer { result ->
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
                                LocalDate.parse(
                                    it,
                                    DateTimeFormatter.ofPattern("yyyy-MM-dd")
                                )
                                    .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                            )
                        }
                        sNotification.isChecked = result?.data?.user?.notifications == true
                        sSubscription.isChecked = result?.data?.user?.subscriptions == true
                    }
                    genderAdapter.setItems(result.data?.genders)

                    result.data?.genders?.forEachIndexed { index, gender ->
                        if (result?.data?.user?.gender == gender.type) {
                            binding.gender.setSelection(index)
                            return@forEachIndexed
                        }
                    }
                }
            }
        })

        viewModel.getUserInfo()
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@ProfileMyDataFragment.viewModel

            birthDate.setOnClickListener {
                showDatePickerDialog()
            }

            saveButton.setOnClickListener {
                hideKeyboard()
                viewModel?.updateUserInfo()?.observe(viewLifecycleOwner, Observer { result ->
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
            }

            gender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel?.selectedGender = genderAdapter.getGender(position).type.toString()
                }
            }

            gender.adapter = genderAdapter
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
    

    private fun processError(error: ApiError?) {
        when (error?.code) {
            Const.API_NO_CONNECTION_STATUS_CODE -> {
                navController.getNavigationResult<Event<Boolean>>()?.observe(viewLifecycleOwner, Observer {
                    if (it.getContentIfNotHandled() == true) viewModel.getUserInfo()
                })
                navController.navigate(R.id.noInternetFragment)
            }
            Const.API_SERVER_FAIL_STATUS_CODE -> {
                navController.getNavigationResult<Event<Boolean>>()?.observe(viewLifecycleOwner, Observer {
                    if (it.getContentIfNotHandled() == true) viewModel.getUserInfo()
                })
                navController.navigate(R.id.serverErrorDialogFragment)
            }
            else -> showError(error)
        }
    }
}