package uz.mod.templatex.ui.cart

import android.app.Application
import androidx.lifecycle.*
import uz.mod.templatex.R
import uz.mod.templatex.extension.moneyFormat
import uz.mod.templatex.model.remote.responce.Cart
import uz.mod.templatex.model.remote.responce.CartProductWrapper
import uz.mod.templatex.model.repository.CartRepository

class CartViewModel constructor(application: Application, val repository: CartRepository): AndroidViewModel(application) {

    var cart = MutableLiveData<Cart>()
    var products = MutableLiveData<List<CartProductWrapper>>()

    var isEditing = MutableLiveData(false)

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

    val selectedProducts: LiveData<List<CartProductWrapper>> = Transformations.map(products) {
        it?.filter { it.selected }
    }

    val buttonText = Transformations.map(isEditing) {
        application.getString(if (it) R.string.action_delete else R.string.action_buy)
    }

    val isButtonValid = MediatorLiveData<Boolean>()
        .apply {
            fun validateFrom() {
                value = isEditing.value == false
                        ||(isEditing.value == true && !selectedProducts.value.isNullOrEmpty())
            }

            addSource(isEditing) { validateFrom() }
            addSource(selectedProducts) { validateFrom() }
        }

    val totalPrice = Transformations.map(products) {
        var sum = 0
        it?.forEach {
            sum += it.totalPrice()
        }
        application.getString(R.string.total_price,(sum.moneyFormat()+ " UZS"))
    }

    fun select(id: Int) {
        val temp = products.value!!

        for (i in temp.indices) {
            if (temp[i].id == id) {
                temp[i].selected = !temp[i].selected
            }
        }
        products.value = temp
    }

    fun selectAll() {
        val temp = products.value!!

        for (i in temp.indices) {
            temp[i].selected = true
        }

        products.value = temp
    }

    fun substracted(id: Int) {
        val temp = products.value!!

        for (i in temp.indices) {
            if (temp[i].id == id && temp[i].quantity!=1) {
                temp[i].quantity--
            }
        }
        products.value = temp
    }

    fun add(id: Int) = repository.updateProductCount(id,1)

    fun sub(id: Int) = repository.updateProductCount(id,-1)

    fun added(id: Int) {
        val temp = products.value!!

        for (i in temp.indices) {
            if (temp[i].id == id) {
                temp[i].quantity++
            }
        }
        products.value = temp
    }

    fun startEditing() {
        isEditing.value = true
    }

    fun stopEditing() {
        isEditing.value = false
    }
}