package uz.mod.templatex.ui.profile.authorized.myOrders

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import uz.mod.templatex.model.repository.profile.MyOrdersRepository

class ProfileMyOrdersViewModel constructor(
    application: Application,
    val repository: MyOrdersRepository
) : AndroidViewModel(application) {

    private val request = MutableLiveData<Boolean>()

    val response = Transformations.switchMap(request) {
        repository.getOrders()
    }

    val isEmpty = Transformations.map(response) {
        it?.data?.isNullOrEmpty()
    }

    fun getOrders() {
        request.value = true
    }
}