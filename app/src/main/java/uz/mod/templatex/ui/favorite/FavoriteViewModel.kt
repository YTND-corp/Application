package uz.mod.templatex.ui.favorite

import android.app.Application
import androidx.lifecycle.*
import uz.mod.templatex.model.local.entity.CategoryGender
import uz.mod.templatex.model.remote.network.Resource
import uz.mod.templatex.model.repository.ProductRepository

class FavoriteViewModel constructor(application: Application, val  repository: ProductRepository): AndroidViewModel(application) {

    private val request = MutableLiveData<Boolean>()
    val response = Transformations.switchMap(request) {
        repository.favorites()
    }

    val isEmpty = Transformations.map(response) {
       it?.data?.isNullOrEmpty() == true
    }

    fun favoriteToggle(id: Int, isFavorite: Boolean) = repository.favoriteToggle(id, isFavorite)

    fun getFavorites() {
        request.value = true
    }
}