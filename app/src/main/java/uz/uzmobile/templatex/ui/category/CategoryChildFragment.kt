package uz.uzmobile.templatex.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.uzmobile.templatex.databinding.CategoryChildFragmentBinding
import uz.uzmobile.templatex.model.local.entity.CategoryGender

class CategoryChildFragment : Fragment() {
    val viewModel: CategoryChildViewModel by viewModel()

    private val binding by lazy { CategoryChildFragmentBinding.inflate(layoutInflater) }

    private lateinit var adapter: CategoryAdapter

    companion object {
        const val GENDER= "catalog_gender"
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

            adapter = CategoryAdapter()

            catalogs.hasFixedSize()
            catalogs.adapter = adapter

        }
    }


}