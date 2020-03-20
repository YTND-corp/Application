package uz.uzmobile.templatex.ui.country

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import uz.uzmobile.templatex.R
import uz.uzmobile.templatex.databinding.CountryFragmentBinding
import uz.uzmobile.templatex.model.local.entity.Country
import uz.uzmobile.templatex.ui.custom.LineDividerItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

class CountryFragment : Fragment() {

    val viewModel: CountryViewModel by viewModel()

    private val binding by lazy { CountryFragmentBinding.inflate(layoutInflater) }

    companion object {
        fun newInstance() = CountryFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this@CountryFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.apply {
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
}
