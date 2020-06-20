package uz.mod.templatex.ui.selection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import uz.mod.templatex.databinding.SelectionChildFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.model.local.entity.HomeGender

class SelectionChildFragment : Fragment() {
    val viewModel: SelectionChildViewModel by viewModel()

    private val binding by lazy { SelectionChildFragmentBinding.inflate(layoutInflater) }

    private lateinit var adapter: SelectionAdapter

    companion object {
        const val GENDER = "DENDER"
        fun newInstance(gender: HomeGender): SelectionChildFragment {
            val fragment = SelectionChildFragment()
            val bundle = Bundle()
            bundle.putParcelable(GENDER, gender)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setArgs(arguments?.getParcelable(GENDER))
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

        initViews()

        viewModel.items.observe(viewLifecycleOwner, Observer {
            adapter.setItems(it)
        })

    }

    private fun initViews() {
        binding.apply {
            viewModel = this@SelectionChildFragment.viewModel
            executePendingBindings()

            adapter = SelectionAdapter()
            selections.hasFixedSize()
            selections.adapter = adapter
        }
    }
}