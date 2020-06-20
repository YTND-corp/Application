package uz.mod.templatex.ui.filter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.androidx.viewmodel.ext.android.viewModel

import uz.mod.templatex.databinding.FilterFragmentBinding

class FilterFragment : Fragment() {

    val viewModel: FilterViewModel by viewModel()

    private val binding by lazy { FilterFragmentBinding.inflate(layoutInflater) }


    companion object {
        fun newInstance() = FilterFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this@FilterFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

    }

    private fun initViews() {
        binding.apply {
            viewModel = this@FilterFragment.viewModel
            executePendingBindings()

        }
    }
}