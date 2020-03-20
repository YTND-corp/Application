package uz.uzmobile.templatex.ui.catalogDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import uz.uzmobile.templatex.R
import uz.uzmobile.templatex.model.local.entity.CatalogDetail
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.uzmobile.templatex.databinding.CatalogDetailsFragmentBinding
import uz.uzmobile.templatex.ui.custom.LineDividerItemDecoration

class CatalogDetailFragment : Fragment() {

    val viewModel: CatalogDetailViewModel by viewModel()

    private val binding by lazy { CatalogDetailsFragmentBinding.inflate(layoutInflater) }

    companion object {
        fun newInstance() = CatalogDetailFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this@CatalogDetailFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@CatalogDetailFragment.viewModel
            executePendingBindings()


            catalogDetails.adapter = CatalogDetailAdapter(listOf(CatalogDetail(0), CatalogDetail(0), CatalogDetail(0)))

            catalogDetails.addItemDecoration(
                LineDividerItemDecoration(
                    requireContext(),
                    R.drawable.list_divider
                )
            )

            continueButton.setOnClickListener {
                findNavController().navigate(R.id.action_global_productsFragment)
            }
        }
    }
}