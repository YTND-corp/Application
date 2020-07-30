package uz.mod.templatex.ui.parent

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import uz.mod.templatex.R
import uz.mod.templatex.model.remote.network.ApiError
import uz.mod.templatex.ui.MainViewModel

open class ParentFragment : Fragment() {

    val sharedViewModel: MainViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findNavController().currentDestination?.let {
            sharedViewModel.destinationChanged(it)
        }
    }

    open fun showLoading() {
        (activity as? ParentActivity)?.showLoading()
    }

    open fun hideLoading() {
        (activity as? ParentActivity)?.hideLoading()
    }

    open fun showError(res: ApiError?) {
        val errorMessage = if (res?.message.isNullOrEmpty())
            requireContext().getString(R.string.unknown_error)
        else res?.message
        showError(errorMessage)
    }

    open fun showError(res: String?) {
        (activity as? ParentActivity)?.showError(res)
    }

    open fun showError(resId: Int) {
        (activity as? ParentActivity)?.showError(resId)
    }

    open fun hideKeyboard() {
        (activity as? ParentActivity)?.hideKeyboard()
    }

}