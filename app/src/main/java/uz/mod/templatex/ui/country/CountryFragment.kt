package uz.mod.templatex.ui.country

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.R
import uz.mod.templatex.databinding.CountryFragmentBinding
import uz.mod.templatex.model.local.entity.Country
import uz.mod.templatex.ui.custom.LineDividerItemDecoration

class CountryFragment : Fragment() {

    val viewModel: CountryViewModel by viewModel()

    private lateinit var binding: CountryFragmentBinding

    companion object {
        fun newInstance() = CountryFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CountryFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews(): Unit = with(binding) {
        viewModel = this@CountryFragment.viewModel
        executePendingBindings()

        countries.adapter = CountryAdapter(listOf(Country(0), Country(0), Country(0)))

        countries.addItemDecoration(
            LineDividerItemDecoration(
                requireContext(),
                R.drawable.list_divider
            )
        )
    }
}
