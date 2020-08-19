package uz.mod.templatex.ui.parent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import uz.mod.templatex.R
import uz.mod.templatex.model.remote.network.ApiError
import uz.mod.templatex.ui.MainViewModel
import uz.mod.templatex.utils.extension.lazyFast
import uz.mod.templatex.utils.extension.toast

abstract class ParentFragment : Fragment() {

    private val navController by lazyFast { findNavController() }
    val sharedViewModel: MainViewModel by sharedViewModel()
    var childBinding: ViewDataBinding? = null
        private set

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val resID = getLayoutID() ?: return super.onCreateView(inflater, container, savedInstanceState)
        childBinding = DataBindingUtil.inflate(inflater, resID, container, false)
        childBinding?.lifecycleOwner = viewLifecycleOwner
        return childBinding?.root
    }

    /** Pass @null If you want do not want to use DataBinding */
    abstract fun getLayoutID() : Int?

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController.currentDestination?.let {
            sharedViewModel.destinationChanged(it)
        }
    }


    override fun onDestroyView() {
        hideKeyboard()
        hideLoading()
        (childBinding?.root?.parent as? ViewGroup)?.removeView(childBinding?.root)
        childBinding = null
        super.onDestroyView()
    }

    override fun onResume() {
        super.onResume()
        activity?.let {
            FirebaseAnalytics.getInstance(it).setCurrentScreen(it, javaClass.simpleName, null)
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

    open fun toast(message: String) {
        (activity as? ParentActivity)?.toast(message)
    }

    open fun toast(@StringRes resID: Int) {
        (activity as? ParentActivity)?.toast(resID)
    }
}