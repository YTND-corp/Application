package uz.mod.templatex.ui.cart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import timber.log.Timber
import uz.mod.templatex.R
import uz.mod.templatex.model.local.CartResponseSafeArgs
import uz.mod.templatex.model.local.entity.Product
import uz.mod.templatex.model.remote.response.Cart
import uz.mod.templatex.model.repository.CartRepository
import uz.mod.templatex.model.repository.FavoriteRepository
import uz.mod.templatex.utils.extension.moneyFormat

class CartViewModel constructor(
    application: Application,
    val repository: CartRepository,
    private val favoriteRepository: FavoriteRepository
) : AndroidViewModel(application) {

    var cart = MutableLiveData<Cart>()
    var cartResponseSafeArgs : CartResponseSafeArgs? = null

    val request = MutableLiveData<Boolean>()
    val response = Transformations.switchMap(request) {
        repository.products()
    }

    private val updateRequest = MutableLiveData<Pair<Int, Int>>()
    val updateResponse = Transformations.switchMap(updateRequest) {
        repository.updateProductCount(it.first, it.second)
    }

    private val deleteRequest = MutableLiveData<Int>()
    val deleteResponse = Transformations.switchMap(deleteRequest) {
        repository.delete(it)
    }

    fun getCart() {
        request.value = true
    }

    var products = Transformations.map(response) {
        cartResponseSafeArgs = CartResponseSafeArgs(it?.data ?: arrayListOf())
        it?.data
    }

    val productCount: LiveData<String> = Transformations.map(products) {
        application.getString(R.string.product_count, it?.size ?: 0)
    }

    val isCartEmpty = Transformations.map(products) {
        it?.isNullOrEmpty() ?: true
    }

    val totalPrice = Transformations.map(products) {
        var sum = 0
        it?.forEach { product ->
            sum += product.totalPrice()
        }
        application.getString(R.string.total_price, (sum.moneyFormat() + " UZS"))
    }

    fun add(product: Product) {
        Timber.e("Add...")
        updateRequest.value = Pair(product.cartProductId, product.quantity + 1)
    }

    fun sub(product: Product) {
        Timber.e("Sub...")
        if (product.quantity > 1) {
            updateRequest.value = Pair(product.cartProductId, product.quantity - 1)
        }
    }

    fun favoriteToggle(id: Int) = favoriteRepository.toggleFavorite(id)

    fun delete(id: Int) {
        deleteRequest.value = id
    }
}