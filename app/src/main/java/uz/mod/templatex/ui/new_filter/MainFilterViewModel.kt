package uz.mod.templatex.ui.new_filter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import uz.mod.templatex.model.local.entity.Filter
import uz.mod.templatex.model.local.entity.FilterAttribute
import uz.mod.templatex.model.repository.ProductRepository
import uz.mod.templatex.ui.new_filter.adapter.MainFilterAdapter

class MainFilterViewModel(application: Application, val repository: ProductRepository) : AndroidViewModel(application){
    lateinit var sharedModel : SharedFilterViewModel
    val saveButtonVisible = MutableLiveData<Boolean>(false)
    var categoryId : Int? = null
        set(value) {
            field = value
            recreateMainFilterList()
        }

    private fun recreateMainFilterList() {
        filterForCategory = categoryId?.let { repository.getFiltersForCategory(it) }
        if (sharedModel.currentFilter?.id != filterForCategory?.id) {
            sharedModel.currentFilter = filterForCategory
        }
        val items = mutableListOf<MainFilterAdapter.MainFilterDataItem<*>>()
        items.add(MainFilterAdapter.TitleItem("Фильтровать"))
        if (filterForCategory?.brands?.size?:0>0) {
            items.add(MainFilterAdapter.BrandItem())
        }
        filterForCategory?.attributes?.forEach {
            items.add(MainFilterAdapter.AttributeItem(it))
        }
        items.add(MainFilterAdapter.TitleItem("Сортировать"))
        items.add(MainFilterAdapter.SortItem(SharedFilterViewModel.SelectedFitlerDto.Companion.Sort.PopularSort()))
        items.add(MainFilterAdapter.SortItem(SharedFilterViewModel.SelectedFitlerDto.Companion.Sort.NewSort()))
        items.add(MainFilterAdapter.SortItem(SharedFilterViewModel.SelectedFitlerDto.Companion.Sort.PriceAscSort()))
        items.add(MainFilterAdapter.SortItem(SharedFilterViewModel.SelectedFitlerDto.Companion.Sort.PriceDescSort()))
        itemsData.postValue(items)
    }

    private var filterForCategory : Filter? = null

    val itemsData = MutableLiveData<List<MainFilterAdapter.MainFilterDataItem<*>>>()


}