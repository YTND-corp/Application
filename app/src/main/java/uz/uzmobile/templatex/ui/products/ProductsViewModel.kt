package uz.uzmobile.templatex.ui.products

import android.app.Application
import androidx.lifecycle.*
import uz.uzmobile.templatex.R
import uz.uzmobile.templatex.model.local.db.dao.ProductDao
import uz.uzmobile.templatex.model.local.entity.Product
import uz.uzmobile.templatex.model.remote.network.Resource
import uz.uzmobile.templatex.model.repository.ProductRepository

class ProductsViewModel constructor(application: Application, val repository: ProductRepository) :
    AndroidViewModel(application) {

    var categoryId: Int = 0
    var page: Int = 1
    var totalCount = ""
    var sort = "popular"
    var brandId: Int = 0

    var title = MutableLiveData<String>()

    private val request = MutableLiveData<Boolean>()
    val response: LiveData<Resource<Int>> = Transformations.switchMap(request) {
        repository.getProducts(categoryId, sort, if (brandId==0) null else arrayOf(brandId.toString()), page)
    }

    fun setArgs(args: ProductsFragmentArgs) {
        categoryId = args.id
        title.value = args.title
        brandId = args.brandId
        refresh()
    }

    fun refresh() {
        page = 1
        request.value = true
    }

    fun favoriteToggle(id: Int, isFavorite: Boolean) = repository.favoriteToggle(id, isFavorite)

    fun getProducts() = repository.getProducts()

    fun loadMore() {
        page++
        request.value = true
    }
}