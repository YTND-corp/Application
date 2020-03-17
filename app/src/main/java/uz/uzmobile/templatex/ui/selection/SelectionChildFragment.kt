package uz.uzmobile.templatex.ui.selection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import uz.uzmobile.templatex.databinding.SelectionChildFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectionChildFragment : Fragment() {
    val viewModel: SelectionChildViewModel by viewModel()

    private val binding by lazy { SelectionChildFragmentBinding.inflate(layoutInflater) }

    companion object {
        const val POSITION= "POSITION"
        fun newInstance(position: Int): SelectionChildFragment {
            val fragment = SelectionChildFragment()
            val bundle = Bundle()
            bundle.putInt(POSITION, position)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this@SelectionChildFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

    }

    private fun initViews() {
        binding.apply {
            viewModel = this@SelectionChildFragment.viewModel
            executePendingBindings()

            catalogs.hasFixedSize()

        }
    }
}