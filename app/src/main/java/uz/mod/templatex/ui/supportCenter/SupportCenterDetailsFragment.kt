package uz.mod.templatex.ui.supportCenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.support_center_details_fragment.*
import uz.mod.templatex.R
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.utils.extension.fromHtml

class SupportCenterDetailsFragment : ParentFragment() {

    private val args: SupportCenterDetailsFragmentArgs by navArgs()

    companion object {
        fun newInstance() = SupportCenterDetailsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.support_center_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(args.page) {
            sharedViewModel.setTitle(title)
            tvTitle.text = title
            tvDescription.text = content.fromHtml()
        }
    }
}