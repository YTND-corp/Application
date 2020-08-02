package uz.mod.templatex.ui.askQuestion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.R
import uz.mod.templatex.databinding.AskQuestionFragmentBinding
import uz.mod.templatex.model.remote.network.ApiError
import uz.mod.templatex.model.remote.network.Status
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.utils.Const
import uz.mod.templatex.utils.Event
import uz.mod.templatex.utils.MaskWatcher
import uz.mod.templatex.utils.PhoneFieldFocusChangeListener
import uz.mod.templatex.utils.extension.getNavigationResult
import uz.mod.templatex.utils.extension.lazyFast

class AskQuestionFragment : ParentFragment() {

    val viewModel: AskQuestionViewModel by viewModel()

    private val navController by lazyFast { findNavController() }
    private val binding by lazy { AskQuestionFragmentBinding.inflate(layoutInflater) }

    companion object {
        fun newInstance() = AskQuestionFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this@AskQuestionFragment
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

    private fun initViews(): Unit = with(binding) {
        viewModel = this@AskQuestionFragment.viewModel
        executePendingBindings()

        regionPhone.text = getString(R.string.ask_question_phone_for_regions, Const.PHONE_NUMBER)
        capitalPhone.text = getString(R.string.ask_question_phone_for_capital, Const.PHONE_NUMBER)

        phone.addTextChangedListener(MaskWatcher.phoneWatcher())
        phone.onFocusChangeListener = PhoneFieldFocusChangeListener()

        sendButton.setOnClickListener {
            hideKeyboard()
            viewModel?.askQuestion()
        }
    }

    private fun processError(error: ApiError?) {
        when (error?.code) {
            Const.API_NO_CONNECTION_STATUS_CODE -> navigateAndObserveResult(R.id.noInternetFragment)
            Const.API_SERVER_FAIL_STATUS_CODE -> navigateAndObserveResult(R.id.serverErrorDialogFragment)
            Const.API_NEW_VERSION_AVAILABLE_STATUS_CODE -> navController.navigate(R.id.newVersionAvailableFragmentDialog)
            else -> showError(error)
        }
    }


    private fun navigateAndObserveResult(@IdRes destinationID: Int) {
        navController.getNavigationResult<Event<Boolean>>()?.observe(viewLifecycleOwner, Observer {
            if (it.getContentIfNotHandled() == true) viewModel.askQuestion()
        })
        navController.navigate(destinationID)
    }
}
