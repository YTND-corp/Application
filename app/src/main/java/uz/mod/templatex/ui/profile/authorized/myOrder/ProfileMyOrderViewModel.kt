package uz.mod.templatex.ui.profile.authorized.myOrder

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import uz.mod.templatex.model.repository.profile.MyOrdersRepository

class ProfileMyOrderViewModel constructor(
    application: Application,
    val repository: MyOrdersRepository
) : AndroidViewModel(application) {

    var isFirstTimeVisible = MutableLiveData<Boolean>()

    fun getOrder(id: Int) = repository.getOrder(id)
}