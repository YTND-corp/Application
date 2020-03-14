package uz.uzmobile.templatex.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.uzmobile.templatex.model.local.entity.Product

class FavoriteViewModel constructor(application: Application): AndroidViewModel(application) {
    val products = MutableLiveData<ArrayList<Product>>()

    init {
        val list = ArrayList<Product>()
        list.add(Product(0))
        list.add(Product(0))
        list.add(Product(0))
        list.add(Product(0))
        list.add(Product(0))
        list.add(Product(0))
        list.add(Product(0))
        list.add(Product(0))
        products.value = list
    }
}