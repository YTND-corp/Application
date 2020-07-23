package uz.mod.templatex.ui.new_filter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import uz.mod.templatex.model.local.entity.Filter
import uz.mod.templatex.model.repository.ProductRepository
import uz.mod.templatex.ui.new_filter.adapter.MainFilterAdapter

class SortViewModel(application: Application) : AndroidViewModel(application) {
    lateinit var sharedModel: SharedFilterViewModel
    val itemsData = MutableLiveData<List<MainFilterAdapter.MainFilterDataItem<*>>>()

    fun getSortList() {
        val items = mutableListOf<MainFilterAdapter.MainFilterDataItem<*>>()
        items.add(MainFilterAdapter.TitleItem("Сортировать"))
        items.add(MainFilterAdapter.SortItem(SharedFilterViewModel.SelectedFitlerDto.Companion.Sort.PopularSort()))
        items.add(MainFilterAdapter.SortItem(SharedFilterViewModel.SelectedFitlerDto.Companion.Sort.NewSort()))
        items.add(MainFilterAdapter.SortItem(SharedFilterViewModel.SelectedFitlerDto.Companion.Sort.PriceAscSort()))
        items.add(MainFilterAdapter.SortItem(SharedFilterViewModel.SelectedFitlerDto.Companion.Sort.PriceDescSort()))
        itemsData.postValue(items)
    }
}