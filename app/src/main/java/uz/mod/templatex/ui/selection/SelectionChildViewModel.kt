package uz.mod.templatex.ui.selection

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import uz.mod.templatex.model.local.entity.HomeGender
import uz.mod.templatex.model.local.entity.HomeItem

class SelectionChildViewModel constructor(application: Application): AndroidViewModel(application) {
    val items = MutableLiveData<List<HomeItem>>()
    fun setArgs(parcelable: HomeGender?) {
        items.value = parcelable?.items
    }
}