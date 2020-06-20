package uz.mod.templatex.ui.subCategory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import uz.mod.templatex.model.local.entity.Category
import uz.mod.templatex.model.local.entity.SubCategory

class SubCategoryViewModel constructor(application: Application): AndroidViewModel(application) {

    val category = MutableLiveData<Category>()
    val details = MutableLiveData<List<SubCategory>>()

    fun setArgs(args: SubCategoryFragmentArgs) {
        category.value = args.category
        details.value = args.category?.details
    }
}
