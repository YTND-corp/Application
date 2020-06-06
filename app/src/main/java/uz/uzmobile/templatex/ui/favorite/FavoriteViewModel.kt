package uz.uzmobile.templatex.ui.favorite

import android.app.Application
import androidx.lifecycle.*
import uz.uzmobile.templatex.model.local.entity.Product
import uz.uzmobile.templatex.model.repository.ProductRepository

class FavoriteViewModel constructor(application: Application, val  repository: ProductRepository): AndroidViewModel(application) {

    fun getFavorites() = repository.getFavorites()

    fun products() = repository.getProducts()

    fun favoriteToggle(id: Int, isFavorite: Boolean) = repository.favoriteToggle(id, isFavorite)
}