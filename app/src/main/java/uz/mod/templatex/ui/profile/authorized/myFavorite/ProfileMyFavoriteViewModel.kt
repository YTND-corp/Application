package uz.mod.templatex.ui.profile.authorized.myFavorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import uz.mod.templatex.model.repository.profile.MyFavoritesRepository

class ProfileMyFavoriteViewModel(
    application: Application,
    val repository: MyFavoritesRepository
) : AndroidViewModel(application) {

    fun getFavorites() = repository.getFavorites()

    fun toggleFavorite(id: Int) = repository.toggleFavorite(id)
}