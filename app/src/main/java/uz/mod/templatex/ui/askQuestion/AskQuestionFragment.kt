package uz.mod.templatex.ui.askQuestion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.R
import uz.mod.templatex.databinding.AskQuestionFragmentBinding
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.utils.Const

class AskQuestionFragment : ParentFragment() {

    val viewModel: AskQuestionViewModel by viewModel()

    private val binding by lazy { AskQuestionFragmentBinding.inflate(layoutInflater) }

    companion object {
        fun newInstance() = AskQuestionFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this@AskQuestionFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews(): Unit = with(binding) {
        viewModel = this@AskQuestionFragment.viewModel
        executePendingBindings()

        regionPhone.text = getString(R.string.ask_question_phone_for_regions, Const.PHONE_NUMBER)
        capitalPhone.text = getString(R.string.ask_question_phone_for_capital, Const.PHONE_NUMBER)
    }
}
