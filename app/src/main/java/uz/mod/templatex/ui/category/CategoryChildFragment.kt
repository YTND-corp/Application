package uz.mod.templatex.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.R
import uz.mod.templatex.databinding.CategoryChildFragmentBinding
import uz.mod.templatex.model.local.entity.CategoryGender
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.utils.extension.lazyFast

class CategoryChildFragment : ParentFragment() {
    val viewModel: CategoryChildViewModel by viewModel()

    private val navController by lazyFast { findNavController() }
    private val binding by lazy { CategoryChildFragmentBinding.inflate(layoutInflater) }

    private lateinit var adapter: CategoryAdapter

    companion object {
        const val GENDER = "catalog_gender"
        fun newInstance(categoryGender: CategoryGender): CategoryChildFragment {
            val fragment = CategoryChildFragment()
            val bundle = Bundle()
            bundle.putParcelable(GENDER, categoryGender)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this@CategoryChildFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setArguments(arguments?.getParcelable(GENDER))

        initViews()


        viewModel.categories.observe(requireActivity(), Observer {
            adapter.setItems(it)
        })
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@CategoryChildFragment.viewModel
            executePendingBindings()

            adapter = CategoryAdapter {
                val action = if (it.subCategory.isNullOrEmpty()) {
                    CategoryFragmentDirections.actionGlobalProductsFragment(
                        it.id,
                        it.name
                    )
                } else {
                    CategoryFragmentDirections.actionCategoryFragmentToSubCategoryFragment(it)
                }
                findNavController().navigate(action)
            }

            catalogs.setHasFixedSize(true)
            catalogs.adapter = adapter

            searchEditText.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus)
                    navController.navigate(R.id.action_global_searchFragment)
            }
        }
    }


}