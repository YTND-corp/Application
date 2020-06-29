package uz.mod.templatex.ui.profile.authorized.myOrders

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import uz.mod.templatex.model.local.entity.Product

class ProfileMyOrdersViewModel constructor(
    application: Application
) : AndroidViewModel(application) {

    var query = MutableLiveData<String>()

    // TODO values for debug, set false - no orders, true - orders exist until API will work
    fun isOrdersAvailable(): Boolean {
        return true
    }

    // TODO dummy data
    fun getOrders(): List<Product> {
        return listOf(
//            Product(id = 0, price = "", isFavorite = false, categoryId = 0, name = "", brand = "", image = ""),
//            Product(id = 0, price = "", isFavorite = false, categoryId = 0, name = "", brand = "", image = ""),
//            Product(id = 0, price = "", isFavorite = false, categoryId = 0, name = "", brand = "", image = "")
        )
    }

    fun search(query: String) {
        this.query.postValue(query)
        // TODO search in recycler view implementation
    }
}