package uz.mod.templatex.ui.new_filter.singleattribute

import androidx.fragment.app.activityViewModels
import uz.mod.templatex.ui.new_filter.SharedFilterViewModel
import uz.mod.templatex.ui.parent.ParentFragment

class SingleAttributeFragment : ParentFragment() {
    val sharedFilterViewModel : SharedFilterViewModel by activityViewModels<SharedFilterViewModel>()
}