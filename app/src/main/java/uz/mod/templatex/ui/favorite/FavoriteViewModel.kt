package uz.mod.templatex.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import timber.log.Timber
import uz.mod.templatex.model.repository.FavoriteRepository

class FavoriteViewModel constructor(application: Application, val repository: FavoriteRepository) :
    AndroidViewModel(application) {

    private val request = MutableLiveData<Boolean>()
    val response = Transformations.switchMap(request) {
        repository.getFavorites()
    }

    val isEmpty = Transformations.map(response) {
        it?.data?.isNullOrEmpty() == true
    }

    fun favoriteToggle(id: Int) = repository.toggleFavorite(id)

    fun getFavorites() {
        Timber.e("Get favorites")
        request.value = true
    }
}