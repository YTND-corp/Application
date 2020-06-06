package uz.uzmobile.templatex.ui.adres

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.uzmobile.templatex.R

import uz.uzmobile.templatex.databinding.AdresFragmentBinding
import uz.uzmobile.templatex.model.local.entity.Adres
import uz.uzmobile.templatex.ui.custom.LineDividerItemDecoration
import uz.uzmobile.templatex.ui.parent.ParentFragment

class AdresFragment : ParentFragment() {

    val viewModel: AdresViewModel by viewModel()

    private val binding by lazy { AdresFragmentBinding.inflate(layoutInflater) }

    companion object {
        fun newInstance() = AdresFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this@AdresFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@AdresFragment.viewModel
            executePendingBindings()


            adreses.adapter = AdresAdapter(listOf(Adres(0), Adres(0), Adres(0)))
            adreses.addItemDecoration(
                LineDividerItemDecoration(
                    requireContext(),
                    R.drawable.divider
                )
            )
        }
    }
}
