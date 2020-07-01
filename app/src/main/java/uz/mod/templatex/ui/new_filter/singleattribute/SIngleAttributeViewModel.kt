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
            val values: List<IValue>?
            if (attributeId==-2){
                values = sharedModel.currentFilter?.brands
            } else {
                values = sharedModel.currentFilter?.attributes?.find { it.id == value }?.values
            }
            values?:return
            itemsData.postValue(values.map { SingleAttributeFilterAdapter.AttributeValueItem(it) })
        }

    val itemsData = MutableLiveData<List<SingleAttributeFilterAdapter.AttributeValueItem>>()

}