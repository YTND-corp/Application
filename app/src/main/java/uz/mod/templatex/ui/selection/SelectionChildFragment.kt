package uz.mod.templatex.ui.selection

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_our_service.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.databinding.SelectionChildFragmentBinding
import uz.mod.templatex.model.local.entity.HomeGender

const val CALL_US = 0
const val ASK_QUESTION = 1
const val WRITE_TO_US = 2
const val DELIVERY_AND_SAMPLE = 3

interface OurServiceClickEvent {
    fun onServiceItemClicked(id: Int)
}

class SelectionChildFragment : Fragment() {
    val viewModel: SelectionChildViewModel by viewModel()

    private val binding by lazy { SelectionChildFragmentBinding.inflate(layoutInflater) }

    private lateinit var adapter: SelectionAdapter
    private lateinit var listener: OurServiceClickEvent

    companion object {
        const val GENDER = "GENDER"
        fun newInstance(gender: HomeGender): SelectionChildFragment {
            val fragment = SelectionChildFragment()
            val bundle = Bundle()
            bundle.putParcelable(GENDER, gender)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (parentFragment is OurServiceClickEvent) listener = parentFragment as OurServiceClickEvent
        else throw ClassCastException("$parentFragment should implement OurServiceClickEvent")
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

    private fun initViews(): Unit = with(binding) {
        viewModel = this@SelectionChildFragment.viewModel
        executePendingBindings()

        adapter = SelectionAdapter()
        selections.hasFixedSize()
        selections.adapter = adapter

        call_us_holder.setOnClickListener { listener.onServiceItemClicked(CALL_US) }
        write_us_holder.setOnClickListener { listener.onServiceItemClicked(WRITE_TO_US) }
        ask_question_holder.setOnClickListener { listener.onServiceItemClicked(ASK_QUESTION) }
        deliveryAndSampleSection.setOnClickListener { listener.onServiceItemClicked(DELIVERY_AND_SAMPLE) }
        askQuestionSection.setOnClickListener { listener.onServiceItemClicked(ASK_QUESTION) }
    }
}