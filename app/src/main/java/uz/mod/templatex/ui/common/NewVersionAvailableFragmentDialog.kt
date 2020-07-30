package uz.mod.templatex.ui.common

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.include_new_version_available.*
import uz.mod.templatex.R
import uz.mod.templatex.utils.Utils

class NewVersionAvailableFragmentDialog : DialogFragment(R.layout.include_new_version_available) {

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
        btnTryAgain.setOnClickListener { gotoMarket() }
    }

    private fun gotoMarket(): Unit = with(requireContext()) {
        setEnable(false)
        Utils.openGooglePlay(this, this.packageName)
        setEnable(true)
    }

    private fun setEnable(isEnable : Boolean) {
        btnTryAgain.isEnabled = isEnable
    }


}