package uz.uzmobile.templatex.ui.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.uzmobile.templatex.databinding.CatalogChildFragmentBinding

class CatalogChildFragment : Fragment() {
    val viewModel: CatalogChildViewModel by viewModel()

    private val binding by lazy { CatalogChildFragmentBinding.inflate(layoutInflater) }

    companion object {
        const val POSITION= "POSITION"
        fun newInstance(position: Int): CatalogChildFragment {
            val fragment = CatalogChildFragment()
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
        binding.lifecycleOwner = this@CatalogChildFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

    }

    private fun initViews() {
        binding.apply {
            viewModel = this@CatalogChildFragment.viewModel
            executePendingBindings()

        }
    }
}