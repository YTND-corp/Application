package uz.mod.templatex.ui.subCategory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import uz.mod.templatex.model.local.entity.Category
import uz.mod.templatex.model.local.entity.CategoryGender
import uz.mod.templatex.model.local.entity.SubCategory
import uz.mod.templatex.model.remote.network.Resource
import uz.mod.templatex.model.repository.CategoryRepository

class SubCategoryViewModel constructor(application: Application, repository: CategoryRepository): AndroidViewModel(application) {

    val category = MutableLiveData<Category>()
    val subCategory = MutableLiveData<List<SubCategory>>()

    private val request = MutableLiveData<Boolean>()
    val response: LiveData<Resource<List<CategoryGender>>> = Transformations.switchMap(request) {
        repository.getCategories()
    }

    fun getCatalogs() {
        request.value = true
    }

    fun setArgs(args: SubCategoryFragmentArgs) {
        category.value = args.category
        subCategory.value = args.category?.subCategory
    }
}
