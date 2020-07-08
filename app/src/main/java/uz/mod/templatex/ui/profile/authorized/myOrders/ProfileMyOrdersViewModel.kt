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

    private val searchText = MutableLiveData<String>()

    val response = Transformations.switchMap(searchText) {
        repository.getOrders(searchText.value ?: "")
    }

    val isEmpty = Transformations.map(response) {
        it?.data?.isNullOrEmpty()
    }

    fun getOrders(searchText: String = "") {
        this.searchText.value = searchText
    }
}