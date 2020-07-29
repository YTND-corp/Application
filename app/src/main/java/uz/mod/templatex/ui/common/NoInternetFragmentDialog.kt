package uz.mod.templatex.ui.common

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.include_no_internet.*
import uz.mod.templatex.R
import uz.mod.templatex.utils.Event
import uz.mod.templatex.utils.NetworkUtil
import uz.mod.templatex.utils.extension.lazyFast
import uz.mod.templatex.utils.extension.setNavigationResult


class NoInternetFragmentDialog : DialogFragment(R.layout.include_no_internet) {

    private val navController by lazyFast { findNavController() }
    private val handler by lazyFast { Handler() }
    private val hideProgressRunnable by lazyFast { Runnable { hideLoading() } }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        super.onCreateDialog(savedInstanceState).apply { requestWindowFeature(Window.FEATURE_NO_TITLE) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
    }

    private fun setClickListeners() {
        closeFragment.setOnClickListener { closeFragment(false) }
        btnTryAgain.setOnClickListener { checkConnectivity() }
    }

    private fun checkConnectivity() {
        toggleBtnEnable(false)
        showLoading()
        if (NetworkUtil.isConnected(requireContext())) {
            hideLoading()
            closeFragment(true)
        } else handler.postDelayed(hideProgressRunnable, 1000)
        toggleBtnEnable(true)
    }

    private fun toggleBtnEnable(isEnable: Boolean) {
        btnTryAgain?.isEnabled = isEnable
    }

    private fun closeFragment(shouldRetry: Boolean) {
        navController.setNavigationResult(Event(shouldRetry))
        dismiss()
    }

    private fun showLoading() {
        loadingProgressIndicator.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loadingProgressIndicator.visibility = View.INVISIBLE
    }

}