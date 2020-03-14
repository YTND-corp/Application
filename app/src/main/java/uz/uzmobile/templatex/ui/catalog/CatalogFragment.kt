package uz.uzmobile.templatex.ui.catalog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.uzmobile.templatex.R
import uz.uzmobile.templatex.databinding.CatalogFragmentBinding

class CatalogFragment : Fragment() {

    val viewModel: CatalogViewModel by viewModel()

    private val binding by lazy { CatalogFragmentBinding.inflate(layoutInflater) }

    companion object {
        fun newInstance() = CatalogFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this@CatalogFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@CatalogFragment.viewModel
            executePendingBindings()
        }
    }
}