package uz.uzmobile.templatex.ui.cart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import uz.uzmobile.templatex.R
import uz.uzmobile.templatex.model.remote.responce.Cart
import uz.uzmobile.templatex.model.remote.responce.CartProductWrapper
import uz.uzmobile.templatex.model.remote.responce.ProductWrapper
import uz.uzmobile.templatex.model.repository.CartRepository

class CartViewModel constructor(application: Application, val repository: CartRepository): AndroidViewModel(application) {

    var cart = MutableLiveData<Cart>()
    var products = MutableLiveData<List<CartProductWrapper>>()

    fun removeProduct(product: CartProductWrapper) = repository.remove(product.id)



    val productCount: LiveData<String> = Transformations.map(products) {
        application.getString(R.string.product_count, it?.size ?: 0)
    }

    val isCartEmpty = Transformations.map(products) {
        it.isNullOrEmpty()
    }

    fun getCart() =  repository.getCart()

    fun setCart(cart: Cart?) {
        this.cart.value = cart
    }

    fun setProducts(products: List<CartProductWrapper>?) {
        this.products.value = products
    }

    fun onItemRemoved(item: CartProductWrapper) {
        products.value.apply {
            this?.filter {it.product?.id != item.product?.id}
        }
    }
}