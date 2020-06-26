package uz.mod.templatex.ui.parent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber
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