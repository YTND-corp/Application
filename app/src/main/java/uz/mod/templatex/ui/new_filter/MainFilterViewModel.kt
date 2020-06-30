package uz.mod.templatex.ui.new_filter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import uz.mod.templatex.model.repository.ProductRepository

class MainFilterViewModel(application: Application, val repository: ProductRepository) :
    AndroidViewModel(application){
    val saveButtonVisible = MutableLiveData<Boolean>(false)
    var categoryId : Int? = null

    fun onAttributeSelected(){

    }
}