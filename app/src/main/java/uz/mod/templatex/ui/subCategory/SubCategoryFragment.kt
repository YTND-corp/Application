package uz.mod.templatex.ui.subCategory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import uz.mod.templatex.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import uz.mod.templatex.databinding.SubCategoryFragmentBinding
import uz.mod.templatex.ui.custom.LineDividerItemDecoration
import uz.mod.templatex.ui.parent.ParentFragment

class SubCategoryFragment : ParentFragment() {

    val viewModel: SubCategoryViewModel by viewModel()

    private val binding by lazy { SubCategoryFragmentBinding.inflate(layoutInflater) }

    val args: SubCategoryFragmentArgs by navArgs()

    private lateinit var adapter: SubCategoryAdapter

    companion object {
        fun newInstance() = SubCategoryFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.e(args.category.toString())
        viewModel.setArgs(args)

        sharedViewModel.setTitle(args.category?.name)

        initViews()

        viewModel.subCategory.observe(viewLifecycleOwner, Observer {
            adapter.setItems(it)
        })
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@SubCategoryFragment.viewModel
            executePendingBindings()

            adapter = SubCategoryAdapter()
            catalogDetails.adapter = adapter

            catalogDetails.addItemDecoration(
                LineDividerItemDecoration(
                    requireContext(),
                    R.drawable.list_divider
                )
            )

            continueButton.setOnClickListener {
                findNavController().navigate(
                    SubCategoryFragmentDirections.actionGlobalProductsFragment(
                        args.category?.id ?: 0,
                        args.category?.name
                    )
                )
            }
        }
    }
}