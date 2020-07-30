package uz.mod.templatex.model.repository

import androidx.lifecycle.LiveData
import timber.log.Timber
import uz.mod.templatex.model.local.db.dao.FavoriteDao
import uz.mod.templatex.model.local.entity.Favorite
import uz.mod.templatex.model.remote.api.FavoritesService
import uz.mod.templatex.model.remote.network.*
import uz.mod.templatex.model.remote.response.FavoritesResponse

class FavoriteRepository(
    val service: FavoritesService,
    val favoritesDao: FavoriteDao,
    val executors: AppExecutors
) {

    init {
        Timber.d("Injection MyFavoritesRepository")
    }

    fun getFavorites(): LiveData<Resource<List<Favorite>>> {
        return object :
            NetworkBoundResource<List<Favorite>, FavoritesResponse>(executors) {
            override fun saveCallResult(item: FavoritesResponse) {
                favoritesDao.deleteAll()
                favoritesDao.insertAll(item.products)
            }

            override fun shouldFetch(data: List<Favorite>?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<List<Favorite>> {
                return favoritesDao.getAll()
            }

            override fun createCall(): LiveData<ApiResponse<FavoritesResponse>> {
                return service.getFavorites()
            }
        }.asLiveData()
    }

    fun toggleFavorite(id: Int): LiveData<Resource<Any>> {
        return object : NetworkOnlyResource<Any, Any>() {
            override fun processResult(item: Any?): Any? {
                favoritesDao.delete(id)
                return item
            }

            override fun createCall(): LiveData<ApiResponse<Any>> {
                return service.toggleFavorite(id)
            }
        }.asLiveData()
    }
}