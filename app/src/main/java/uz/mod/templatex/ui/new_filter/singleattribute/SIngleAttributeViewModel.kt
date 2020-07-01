package uz.mod.templatex.ui.new_filter.singleattribute

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import uz.mod.templatex.model.local.entity.AttributeValue
import uz.mod.templatex.model.local.entity.IValue
import uz.mod.templatex.ui.new_filter.SharedFilterViewModel
import uz.mod.templatex.ui.new_filter.adapter.SingleAttributeFilterAdapter

class SingleAttributeViewModel(application: Application) : AndroidViewModel(application){
    lateinit var sharedModel : SharedFilterViewModel

    var attributeId : Int = -1
        set(value) {
            field = value
            val values: List<IValue>? = buildValues(value)
            values?:return
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

    val itemsData = MutableLiveData<List<SingleAttributeFilterAdapter.AttributeValueItem>>()

    fun onQueryChanged(query : String){
        if (query == null || query.isEmpty()){
            itemsData.postValue(buildValues()?.let { mapValuesToItems(it) })
        } else {
            itemsData.postValue(buildValues()?.filter { it.name?.toLowerCase()?.contains(query?.toLowerCase())?:false }?.let { mapValuesToItems(it) })
        }
    }

    fun applyChanges(){
        sharedModel.saveTemporaryData()
    }

}