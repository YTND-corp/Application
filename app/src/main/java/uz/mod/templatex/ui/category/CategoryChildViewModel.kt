package uz.mod.templatex.ui.category

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import uz.mod.templatex.model.local.entity.Category
import uz.mod.templatex.model.local.entity.CategoryGender

class CategoryChildViewModel constructor(application: Application): AndroidViewModel(application) {
    fun setArguments(gender: CategoryGender?) {
        categories.value = gender?.categories//?: arrayListOf<Catalog>()
    }

    val categories = MutableLiveData<List<Category>>()

    init {

    }
}
