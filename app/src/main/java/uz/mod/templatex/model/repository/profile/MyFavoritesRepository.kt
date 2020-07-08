package uz.mod.templatex.model.repository.profile

import androidx.lifecycle.LiveData
import okhttp3.MediaType
import okhttp3.RequestBody
import timber.log.Timber
import uz.mod.templatex.model.local.db.dao.ProfileFavoriteDao
import uz.mod.templatex.model.local.entity.profile.ProfileFavorite
import uz.mod.templatex.model.remote.api.profile.MyFavoritesService
import uz.mod.templatex.model.remote.network.*
import uz.mod.templatex.model.remote.response.profile.MyFavoritesResponse

class MyFavoritesRepository(
    val service: MyFavoritesService,
    val favoritesDao: ProfileFavoriteDao,
    val executors: AppExecutors
) {

    init {
        Timber.d("Injection MyFavoritesRepository")
    }

    fun getFavorites(): LiveData<Resource<List<ProfileFavorite>>> {
        return object :
            NetworkBoundResource<List<ProfileFavorite>, MyFavoritesResponse>(executors) {
            override fun saveCallResult(item: MyFavoritesResponse) {
                favoritesDao.deleteAll()
                favoritesDao.insertAll(item.products)
            }

            override fun shouldFetch(data: List<ProfileFavorite>?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<List<ProfileFavorite>> {
                return favoritesDao.getAll()
            }

            override fun createCall(): LiveData<ApiResponse<MyFavoritesResponse>> {
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
                return service.toggleFavorite(
                    RequestBody.create(
                        MediaType.parse("text/plain"),
                        id.toString()
                    )
                )
            }
        }.asLiveData()
    }
}