package uz.mod.templatex.ui.cart

import android.app.Application
import androidx.lifecycle.*
import timber.log.Timber
import uz.mod.templatex.R
import uz.mod.templatex.model.local.entity.Product
import uz.mod.templatex.utils.extension.moneyFormat
import uz.mod.templatex.model.remote.response.Cart
import uz.mod.templatex.model.repository.CartRepository

class CartViewModel constructor(application: Application, val repository: CartRepository): AndroidViewModel(application) {

    var cart = MutableLiveData<Cart>()



    val request = MutableLiveData<Boolean>()
    val response = Transformations.switchMap(request) {
        repository.products()
    }

    private val updateRequest = MutableLiveData<Int>()
    val updateResponse = Transformations.switchMap(updateRequest) {
        repository.updateProductCount(37, it)
    }

    private val deleteRequest = MutableLiveData<Boolean>()
    val deleteResponse = Transformations.switchMap(deleteRequest) {
        val temps = arrayListOf<Int>()
        selectedProducts?.value?.forEach {
            temps.add(it.id)
        }

        repository.delete(temps)
    }

    fun getCart() {
        request.value = true
    }

    var products = Transformations.map(response) {
        it?.data
    }

    var isEditing = MutableLiveData(false)

    val productCount: LiveData<String> = Transformations.map(products) {
        application.getString(R.string.product_count, it?.size ?: 0)
    }

    val isCartEmpty = Transformations.map(products) {
        it?.isNullOrEmpty()?: true
    }

    val selectedProducts: LiveData<List<Product>> = Transformations.map(products) {
        it?.filter { it.selected }
    }

   fun delete() {
        deleteRequest.value = true
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

    fun select(product: Product) {
        repository.select(product.id, !product.selected)
    }

    fun add(product: Product)  {
        Timber.e("Add...")
        updateRequest.value =  product.quantity + 1
    }

    fun sub(product: Product)  {
        Timber.e("Sub...")
        if (product.quantity>1) {
            updateRequest.value = product.quantity - 1
        }
    }


    fun startEditing() {
        isEditing.value = true
    }

    fun stopEditing() {
        isEditing.value = false
    }
}