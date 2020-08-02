package uz.mod.templatex.ui.new_filter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import uz.mod.templatex.model.local.entity.Filter
import uz.mod.templatex.model.repository.ProductRepository
import uz.mod.templatex.ui.new_filter.adapter.MainFilterAdapter

class MainFilterViewModel(application: Application, val repository: ProductRepository) : AndroidViewModel(application) {

    lateinit var sharedModel: SharedFilterViewModel
    private var filterForCategory: Filter? = null
    val itemsData = MutableLiveData<List<MainFilterAdapter.MainFilterDataItem<*>>>()
    val isResetButtonVisible = MutableLiveData<Boolean>()

    var categoryId: Int? = null
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
        if (filterForCategory?.brands?.size ?: 0 > 0) {
            items.add(MainFilterAdapter.BrandItem())
        }
        filterForCategory?.attributes?.forEach {
            items.add(MainFilterAdapter.AttributeItem(it))
        }
        itemsData.postValue(items)
    }

    fun onResetClick() {
        sharedModel.currentFilter?.brands?.forEach {
            it.selected = false
        }
        sharedModel.currentFilter?.attributes?.forEach {
            it.values?.forEach { value ->
                value.selected = false
            }
        }
        sharedModel.needToReloadFeed = true
        isResetButtonVisible.value = false
    }

    fun shouldShowResetButton() {
        if (sharedModel.currentFilter?.brands?.find { it.selected } != null) {
            isResetButtonVisible.value = true
            return
        }

        sharedModel.currentFilter?.attributes?.forEach {
            it.values?.forEach { value ->
                if (value.selected) {
                    isResetButtonVisible.value = true
                    return
                }
            }
        }

        isResetButtonVisible.value = false
    }
}