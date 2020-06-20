package uz.mod.templatex.ui.product

import android.app.Application
import androidx.lifecycle.*
import timber.log.Timber
import uz.mod.templatex.model.remote.network.Resource
import uz.mod.templatex.model.remote.responce.ProductColor
import uz.mod.templatex.model.remote.responce.ProductDetailResponse
import uz.mod.templatex.model.remote.responce.ProductSize
import uz.mod.templatex.model.repository.ProductRepository
import uz.mod.templatex.utils.Const

class ProductViewModel constructor(application: Application, val repository: ProductRepository): AndroidViewModel(application) {

    private val request = MutableLiveData<Boolean>()
    private var productId: Int = 0
    private var categoryId: Int = 0

    var selectedColor = MutableLiveData<ProductColor>()
    var selectedSize = MutableLiveData<ProductSize>()


    val response: LiveData<Resource<ProductDetailResponse>> = Transformations.switchMap(request) {
        repository.getProduct(categoryId,productId)
    }

    fun setArgs(args: ProductFragmentArgs) {
        categoryId = args.categoryId
        productId = args.productId
        request.value = true
        Timber.e("Category = ${args.categoryId}  product = ${args.productId}")
    }

    val product = Transformations.map(response) {
        it.data?.product
    }

    val delivery = Transformations.map(response) {
        it.data?.delivery
    }

    val images = Transformations.map(product) {
        it?.image
    }

    val hasDescription = Transformations.map(product) {
        !it?.description.isNullOrEmpty()
    }

    val hasComposition = Transformations.map(product) {
        !it?.compositionAndCare().isNullOrEmpty()
    }

    val  colors = Transformations.map(product) {
        it?.attributeCombination?.colors
    }

    val  sizes = Transformations.map(product) {
        it?.attributeCombination?.sizes
    }

    private fun  attributeIds(): ArrayList<String>  {
        val temps: ArrayList<String> = arrayListOf()
        selectedColor.value?.let {
            temps.add(it.id.toString())
        }
        selectedSize.value?.let {
            temps.add(it.id.toString())
        }
        return  temps
    }

    fun addToCart() = repository.addToCart(productId,1, attributeIds())

    val VK_URL = Const.VK_URL
    val INSTAGRAM_URL = Const.INSTAGRAM_URL
    val TELEGRAM_URL = Const.TELEGRAM_URL
    val TIKTOK_URL = Const.TIKTOK_URL
    val FACEBOOK_URL = Const.FACEBOOK_URL
}
