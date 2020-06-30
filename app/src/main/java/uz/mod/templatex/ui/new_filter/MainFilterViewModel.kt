package uz.mod.templatex.ui.new_filter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import uz.mod.templatex.model.local.entity.FilterAttribute
import uz.mod.templatex.model.repository.ProductRepository
import uz.mod.templatex.ui.new_filter.adapter.MainFilterAdapter

class MainFilterViewModel(application: Application, val repository: ProductRepository) :
    AndroidViewModel(application){

    val saveButtonVisible = MutableLiveData<Boolean>(false)
    var categoryId : Int? = null

    val itemsData = MutableLiveData<List<MainFilterAdapter.MainFilterDataItem<*>>>(
        listOf(
            MainFilterAdapter.TitleItem("Title 1"),
            MainFilterAdapter.AttributeItem(FilterAttribute(1,"Attr 1", emptyList())),
            MainFilterAdapter.AttributeItem(FilterAttribute(1,"Attr 2", emptyList())),
            MainFilterAdapter.AttributeItem(FilterAttribute(1,"Attr 3", emptyList())),
            MainFilterAdapter.AttributeItem(FilterAttribute(1,"Attr 4", emptyList())),
            MainFilterAdapter.TitleItem("Title 2"),
            MainFilterAdapter.SortItem("Sort 1"),
            MainFilterAdapter.SortItem("Sort 2"),
            MainFilterAdapter.SortItem("Sort 3"),
            MainFilterAdapter.SortItem("Sort 4"),
            MainFilterAdapter.SortItem("Sort 5")
        )
    )


}