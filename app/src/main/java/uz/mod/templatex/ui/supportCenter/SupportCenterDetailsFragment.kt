package uz.mod.templatex.ui.supportCenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.mod.templatex.R
import uz.mod.templatex.ui.parent.ParentFragment

class SupportCenterDetailsFragment : ParentFragment() {

    //TODO This is not the final version of the fragment. This fragment has been added for demonstration purposes so far.
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.support_center_details_fragment, container, false)
    }
}