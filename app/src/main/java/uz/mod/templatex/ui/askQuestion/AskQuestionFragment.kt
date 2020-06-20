package uz.mod.templatex.ui.askQuestion

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.R

import uz.mod.templatex.databinding.AskQuestionFragmentBinding

class AskQuestionFragment : Fragment() {

    val viewModel: AskQuestionViewModel by viewModel()

    private val binding by lazy { AskQuestionFragmentBinding.inflate(layoutInflater) }

    companion object {
        fun newInstance() = AskQuestionFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.ask_question_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.askQuestionFragment -> true;
            else -> super.onOptionsItemSelected(item)
        }
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

    private fun initViews() {
        binding.apply {
            viewModel = this@AskQuestionFragment.viewModel
            executePendingBindings()
        }
    }
}
