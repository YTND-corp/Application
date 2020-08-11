package uz.mod.templatex.ui.code

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.R
import uz.mod.templatex.databinding.CodeFragmentBinding
import uz.mod.templatex.model.remote.network.ApiError
import uz.mod.templatex.model.remote.network.Status
import uz.mod.templatex.ui.address.AddressFragmentDirections
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.utils.Const
import uz.mod.templatex.utils.Event
import uz.mod.templatex.utils.extension.getNavigationResult
import uz.mod.templatex.utils.extension.lazyFast

class CodeFragment : ParentFragment() {


    private val navController by lazyFast { findNavController() }
    private val codeViewModel: CodeViewModel by viewModel()
    val args: CodeFragmentArgs by navArgs()
    private val binding by lazy { CodeFragmentBinding.inflate(layoutInflater) }
    private lateinit var countDownTimer: CountDownTimer

    companion object {
        fun newInstance() = CodeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        codeViewModel.setArguments(args)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this@CodeFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        if (args.isCheckout) {
            codeViewModel.checkoutConfirmResponse.observe(viewLifecycleOwner, Observer {
                it.getContentIfNotHandled()?.let { result ->
                    when (result.status) {
                        Status.LOADING -> showLoading()
                        Status.ERROR -> {
                            hideLoading()
                            processError(result.error)
                        }
                        Status.SUCCESS -> {
                            hideLoading()
                            binding.code.text = null
                            sharedViewModel.loggedIn(result.data?.user)
                            if (args.isCheckout) {
                                navController.navigate(
                                    AddressFragmentDirections.actionGlobalAddressFragment(
                                        args.cartResponse,
                                        result.data,
                                        args.phone
                                    )
                                )
                            } else {
                                navController.popBackStack(R.id.profileFragment, true)
                            }
                        }
                    }
                }
            })
        } else {
            codeViewModel.authConfirmResponse.observe(viewLifecycleOwner, Observer { result ->
                when (result.status) {
                    Status.LOADING -> showLoading()
                    Status.ERROR -> {
                        hideLoading()
                        processError(result.error)
                    }
                    Status.SUCCESS -> {
                        hideLoading()
                        binding.code.text = null
                        sharedViewModel.loggedIn(result.data)
                        navController.navigate(R.id.action_codeFragment_to_profileFragment)
                    }
                }
            })
        }
    }

    private fun initViews(): Unit = with(binding) {
        viewModel = codeViewModel
        executePendingBindings()

        codeViewModel.code.observe(viewLifecycleOwner, Observer {
            if (it.length != 4) return@Observer
            if (args.isCheckout) codeViewModel.checkoutConfirm()
            else codeViewModel.authConfirm()
        })


        startResendTimer()

        resendButton.setOnClickListener {}

        change.setOnClickListener {
            navController.popBackStack()
        }
    }

    private fun startResendTimer() {
        countDownTimer = object : CountDownTimer(
            codeViewModel.getCountDownTimerPeek(sharedViewModel.countDownTimerMeta),
            1000
        ) {
            override fun onFinish() {
                sharedViewModel.countDownTimerMeta.lastTick = null
                codeViewModel.countDownTimer.value = null
            }

            override fun onTick(time: Long) {
                sharedViewModel.countDownTimerMeta.lastTick = time
                codeViewModel.countDownTimer.value = time
            }
        }.start()
    }


    //TODO("PLEASE CHECK FOLLOWING LINES IF THE LOGIC IS NOT BROKEN")
    private fun processError(error: ApiError?) = when (error?.code) {
        Const.API_NO_CONNECTION_STATUS_CODE -> navigateAndObserveResult(R.id.noInternetFragment)
        Const.API_SERVER_FAIL_STATUS_CODE -> navigateAndObserveResult(R.id.serverErrorDialogFragment)
        Const.API_NEW_VERSION_AVAILABLE_STATUS_CODE -> navController.navigate(R.id.newVersionAvailableFragmentDialog)
        else -> showError(error)
    }


    private fun navigateAndObserveResult(@IdRes destinationID: Int) {
        navController.getNavigationResult<Event<Boolean>>()?.observe(viewLifecycleOwner, Observer {
            if (it.getContentIfNotHandled() == true) retryLastRequest()
        })
        navController.navigate(destinationID)
    }

    private fun retryLastRequest() {
        if (args.isCheckout) codeViewModel.checkoutConfirm()
        else codeViewModel.authConfirm()
    }
}
