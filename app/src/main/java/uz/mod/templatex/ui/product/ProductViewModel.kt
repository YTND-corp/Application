package uz.mod.templatex.ui.product

import android.app.Application
import androidx.lifecycle.*
import uz.mod.templatex.model.remote.network.Resource
import uz.mod.templatex.model.remote.response.ProductColor
import uz.mod.templatex.model.remote.response.ProductDetailResponse
import uz.mod.templatex.model.remote.response.ProductSize
import uz.mod.templatex.model.repository.ProductRepository
import uz.mod.templatex.utils.Const
import uz.mod.templatex.utils.extension.clear

class ProductViewModel constructor(application: Application, val repository: ProductRepository) :
    AndroidViewModel(application) {

    private val request = MutableLiveData<Boolean>()
    private var id: Int = 0

    var selectedColor = MutableLiveData<ProductColor>()
    var selectedSize = MutableLiveData<ProductSize>()


    val response: LiveData<Resource<ProductDetailResponse>> = Transformations.switchMap(request) {
        repository.getProduct(id)
    }

    fun setArgs(args: ProductFragmentArgs) {
        id = args.productId
        request.value = true
    }

    val product = Transformations.map(response) {
        it.data?.product
    }

    val images = MediatorLiveData<List<String>?>()
        .apply {
            fun validateFrom() {
                value =
                    if (!selectedColor.value?.images.isNullOrEmpty()) selectedColor.value?.images else product.value?.images
            }

            addSource(selectedColor) { validateFrom() }
            addSource(product) { validateFrom() }
        }

    val delivery = Transformations.map(response) {
        it.data?.delivery
    }


    val hasDescription = Transformations.map(product) {
        !it?.description.isNullOrEmpty()
    }

    val hasComposition = Transformations.map(product) {
        !it?.compositionAndCare().isNullOrEmpty()
    }

    val colors = Transformations.map(product) {
        it?.attributeCombination?.colorWrapper?.colors
    }

    val sizes = Transformations.map(product) {
        it?.attributeCombination?.sizeWrapper?.sizes
    }

    private fun attributeIds(): ArrayList<Int> {
        val temps: ArrayList<Int> = arrayListOf()
        selectedColor.value?.let {
            temps.add(it.id)
        }
        selectedSize.value?.let {
            temps.add(it.id)
        }
        return temps
    }

    fun addToCart() = repository.addToCart(id, 1, attributeIds())

    fun isFavorite() = Transformations.map(repository.isFavorite(id)) {
        it?.isFavorite == true
    }

    fun favoriteToggle() = repository.favoriteToggle(id)

    val VK_URL = Const.VK_URL
    val INSTAGRAM_URL = Const.INSTAGRAM_URL
    val TELEGRAM_URL = Const.TELEGRAM_URL
    val TIKTOK_URL = Const.TIKTOK_URL
    val FACEBOOK_URL = Const.FACEBOOK_URL

    fun setSelectedColor(item: ProductColor?) {
        selectedColor.value = item
    }


    fun setSelectedSize(item: ProductSize?) {
        selectedSize.value = item
    }
}
