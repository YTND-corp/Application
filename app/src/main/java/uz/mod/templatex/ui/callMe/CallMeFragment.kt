package uz.mod.templatex.ui.callMe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.R
import uz.mod.templatex.databinding.CallMeFragmentBinding
import uz.mod.templatex.model.remote.network.ApiError
import uz.mod.templatex.model.remote.network.Status
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.utils.Const
import uz.mod.templatex.utils.Event
import uz.mod.templatex.utils.MaskWatcher
import uz.mod.templatex.utils.PhoneFieldFocusChangeListener
import uz.mod.templatex.utils.extension.getNavigationResult
import uz.mod.templatex.utils.extension.lazyFast

class CallMeFragment : ParentFragment() {

    val viewModel: CallMeViewModel by viewModel()

    private val navController by lazyFast { findNavController() }
    private val binding by lazy { CallMeFragmentBinding.inflate(layoutInflater) }

    companion object {
        fun newInstance() = CallMeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this@CallMeFragment
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
                    toast(R.string.request_sent_successfully)
                }
            }
        })
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@CallMeFragment.viewModel
            executePendingBindings()

            etPhone.addTextChangedListener(MaskWatcher.phoneWatcher())
            etPhone.onFocusChangeListener = PhoneFieldFocusChangeListener()

            sendButton.setOnClickListener {
                hideKeyboard()
                viewModel?.callback()
            }
        }
    }

    private fun processError(error: ApiError?) {
        when (error?.code) {
            Const.API_NO_CONNECTION_STATUS_CODE -> {
                navController.getNavigationResult<Event<Boolean>>()?.observe(viewLifecycleOwner, Observer {
                    if (it.getContentIfNotHandled() == true) viewModel.callback()
                })
                navController.navigate(R.id.noInternetFragment)
            }
            Const.API_SERVER_FAIL_STATUS_CODE -> {
                navController.getNavigationResult<Event<Boolean>>()?.observe(viewLifecycleOwner, Observer {
                    if (it.getContentIfNotHandled() == true) viewModel.callback()
                })
                navController.navigate(R.id.serverErrorDialogFragment)
            }
            else -> showError(error)
        }
    }
}