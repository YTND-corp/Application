package uz.mod.templatex.ui.new_filter.singleattribute

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import uz.mod.templatex.model.local.entity.IValue
import uz.mod.templatex.ui.new_filter.SharedFilterViewModel
import uz.mod.templatex.ui.new_filter.adapter.SingleAttributeFilterAdapter

class SingleAttributeViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var sharedModel: SharedFilterViewModel
    val itemsData = MutableLiveData<List<SingleAttributeFilterAdapter.AttributeValueItem>>()
    val isResetButtonVisible = MutableLiveData<Boolean>()
    val updateList = MutableLiveData<Boolean>()

    var attributeId: Int = -1
        set(value) {
            field = value
            val values: List<IValue>? = buildValues(value)
            values ?: return
            itemsData.postValue(mapValuesToItems(values))
        }

    private fun mapValuesToItems(values: List<IValue>) =
        values.map { SingleAttributeFilterAdapter.AttributeValueItem(it) }

    private fun buildValues(value: Int = attributeId): List<IValue>? {
        val values: List<IValue>?
        if (attributeId == -2) {
            values = sharedModel.cachedFilter?.brands
        } else {
            values = sharedModel.cachedFilter?.attributes?.find { it.id == value }?.values
        }
        return values
    }

    fun onQueryChanged(query: String) {
        if (query == null || query.isEmpty()) {
            itemsData.postValue(buildValues()?.let { mapValuesToItems(it) })
        } else {
            itemsData.postValue(buildValues()?.filter { it.name?.toLowerCase()?.contains(query?.toLowerCase()) ?: false }
                ?.let { mapValuesToItems(it) })
        }
    }

    fun applyChanges() {
        sharedModel.saveTemporaryData()
    }

    fun onItemClick(attributeItem: SingleAttributeFilterAdapter.AttributeValueItem) {
        if (attributeId == -2) {
            val find = sharedModel.cachedFilter?.brands?.find { it.id == attributeItem.attribute.id }
            find?.apply { selected = !selected }
            shouldShowResetButton()
        } else {
            val find =
                sharedModel.cachedFilter?.attributes?.find { it.id == attributeId }?.values?.find { it.id == attributeItem.attribute.id }
            find?.apply { selected = !selected }
            shouldShowResetButton()
        }

        updateList.value = true
    }

    fun onResetClick() {
        if (attributeId == -2) {
            sharedModel.cachedFilter?.brands?.forEach {
                it.selected = false
            }
        } else {
            sharedModel.cachedFilter?.attributes?.find { it.id == attributeId }?.values?.forEach {
                it.selected = false
            }
        }
        isResetButtonVisible.value = false
        updateList.value = true
    }

    fun shouldShowResetButton() {
        if (attributeId == -2) {
            isResetButtonVisible.value = sharedModel.cachedFilter?.brands?.find { it.selected } != null
        } else {
            isResetButtonVisible.value =
                sharedModel.cachedFilter?.attributes?.find { it.id == attributeId }?.values?.find { it.selected } != null
        }
    }
}