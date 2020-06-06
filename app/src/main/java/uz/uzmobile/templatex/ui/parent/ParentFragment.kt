package uz.uzmobile.templatex.ui.parent

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber
import uz.uzmobile.templatex.R
import uz.uzmobile.templatex.model.remote.network.ApiError
import uz.uzmobile.templatex.ui.main.MainViewModel

open class ParentFragment : Fragment() {

    val sharedViewModel: MainViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findNavController().currentDestination?.let {
            sharedViewModel.destinationChanged(it)
        }
    }

    open fun showLoading() {
        Timber.e("Show Loading")
        (activity as? ParentActivity)?.showLoading()
    }

    open fun hideLoading() {
        Timber.e("Hide Loading")

        (activity as? ParentActivity)?.hideLoading()
    }

    open fun showError(res: ApiError?) {
        (activity as? ParentActivity)?.showError(res)
    }

    open fun showError(res: String?) {
        (activity as? ParentActivity)?.showError(res)
    }

    open fun showError(resId: Int) {
        (activity as? ParentActivity)?.showError(resId)
    }
}