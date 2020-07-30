package uz.mod.templatex.ui.common

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.include_server_error.*
import uz.mod.templatex.R
import uz.mod.templatex.utils.Event
import uz.mod.templatex.utils.extension.lazyFast
import uz.mod.templatex.utils.extension.setNavigationResult

class ServerErrorDialogFragment : DialogFragment(R.layout.include_server_error) {

    private val navController by lazyFast { findNavController() }

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
        btnTryAgain.setOnClickListener { closeFragment() }
    }

    private fun closeFragment() {
        navController.setNavigationResult(Event(true))
        dismiss()
    }
}