package uz.mod.templatex.ui.support

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import uz.mod.templatex.R
import uz.mod.templatex.databinding.SupportFragmentBinding
import uz.mod.templatex.model.local.entity.Support
import uz.mod.templatex.ui.custom.LineDividerItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

class SupportFragment : Fragment() {

    val viewModel: SupportViewModel by viewModel()

    private val binding by lazy { SupportFragmentBinding.inflate(layoutInflater) }

    companion object {
        fun newInstance() = SupportFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this@SupportFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@SupportFragment.viewModel
            executePendingBindings()


            supports.adapter = SupportAdapter(listOf(Support(0), Support(0), Support(0)))

            supports.addItemDecoration(
                LineDividerItemDecoration(
                    requireContext(),
                    R.drawable.list_divider
                )
            )
        }
    }
}
