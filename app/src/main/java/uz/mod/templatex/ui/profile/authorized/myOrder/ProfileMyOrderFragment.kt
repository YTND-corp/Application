package uz.mod.templatex.ui.profile.authorized.myOrder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_activity.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.databinding.ProfileMyOrderFragmentBinding
import uz.mod.templatex.ui.MainActivity
import uz.mod.templatex.ui.parent.ParentFragment

class ProfileMyOrderFragment : ParentFragment() {

    val viewModel: ProfileMyOrderViewModel by viewModel()

    private val binding by lazy { ProfileMyOrderFragmentBinding.inflate(layoutInflater) }

    companion object {
        fun newInstance() = ProfileMyOrderFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this@ProfileMyOrderFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
//        activity?.actionBar?.title = "№4324235"
        (activity as MainActivity).app_bar_layout?.toolbar?.title = "№4324235"
        binding.apply {
            viewModel = this@ProfileMyOrderFragment.viewModel
            executePendingBindings()
        }
    }
}