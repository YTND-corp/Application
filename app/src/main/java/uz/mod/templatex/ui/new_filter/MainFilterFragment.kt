package uz.mod.templatex.ui.new_filter

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import uz.mod.templatex.ui.parent.ParentFragment

class MainFilterFragment : ParentFragment() {
    val sharedFilterViewModel : SharedFilterViewModel by activityViewModels<SharedFilterViewModel>()

}