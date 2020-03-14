package uz.uzmobile.templatex.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.uzmobile.templatex.R
import uz.uzmobile.templatex.databinding.FavoriteFragmentBinding

class FavoriteFragment: Fragment() {

    val viewModel: FavoriteViewModel by viewModel()

    private val binding  by lazy { FavoriteFragmentBinding.inflate(layoutInflater) }

    companion object {
        fun newInstance() = FavoriteFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding.lifecycleOwner = this@FavoriteFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@FavoriteFragment.viewModel
            executePendingBindings()
        }
    }
}
