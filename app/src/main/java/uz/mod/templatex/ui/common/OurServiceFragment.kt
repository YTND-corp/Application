package uz.mod.templatex.ui.common

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_our_service.*
import uz.mod.templatex.R
import uz.mod.templatex.ui.parent.ParentFragment

const val CALL_US = 0
const val ASK_QUESTION = 1
const val WRITE_TO_US = 2
const val DELIVERY_AND_SAMPLE = 3

interface OurServiceClickEvent {
    fun onServiceItemClicked(id: Int)
}

class OurServiceFragment : ParentFragment() {


    private lateinit var listener: OurServiceClickEvent

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (parentFragment is OurServiceClickEvent) listener = parentFragment as OurServiceClickEvent
        else throw ClassCastException("$parentFragment should implement OurServiceClickEvent")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_our_service, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
    }

    private fun setClickListeners() {
        call_us_holder.setOnClickListener { listener.onServiceItemClicked(CALL_US) }
        write_us_holder.setOnClickListener { listener.onServiceItemClicked(WRITE_TO_US) }
        ask_question_holder.setOnClickListener { listener.onServiceItemClicked(ASK_QUESTION) }
        deliveryAndSampleSection.setOnClickListener { listener.onServiceItemClicked(DELIVERY_AND_SAMPLE) }
        askQuestionSection.setOnClickListener { listener.onServiceItemClicked(ASK_QUESTION) }
    }

}