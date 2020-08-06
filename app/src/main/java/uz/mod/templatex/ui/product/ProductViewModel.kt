package uz.mod.templatex.ui.product

import android.app.Application
import androidx.lifecycle.*
import uz.mod.templatex.model.remote.network.Resource
import uz.mod.templatex.model.remote.network.Status
import uz.mod.templatex.model.remote.response.ProductColor
import uz.mod.templatex.model.remote.response.ProductDetailResponse
import uz.mod.templatex.model.remote.response.ProductSize
import uz.mod.templatex.model.repository.CartRepository
import uz.mod.templatex.model.repository.ProductRepository
import uz.mod.templatex.utils.Const

class ProductViewModel constructor(
    application: Application,
    val repository: ProductRepository,
    val cartRepository: CartRepository
) :
    AndroidViewModel(application) {

    private val request = MutableLiveData<Boolean>()
    private var id: Int = 0

    var selectedColor = MutableLiveData<ProductColor>()
    var selectedSize = MutableLiveData<ProductSize>()

    val response: LiveData<Resource<ProductDetailResponse>> = Transformations.switchMap(request) {
        repository.getProduct(id)
    }

    val isContentVisible: LiveData<Boolean> = Transformations.map(response) {
        it.status == Status.SUCCESS
    }

    fun setArgs(args: ProductFragmentArgs) {
        id = args.productId
        sendRequest()
    }

    fun sendRequest() {
        request.value = true
    }

    val shareText = Transformations.map(response) { result ->
        result.data?.product?.let { "${it.brand} ${it.name} ${it.images?.firstOrNull()}" }
    }

    val product = Transformations.map(response) {
        it.data?.product
    }

    val relativeProducts = Transformations.map(response) {
        it.data?.similarByBrand
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

    val hasComposition = Transformations.map(product) {
        !it?.compositionAndCare().isNullOrEmpty()
    }

    val colors = Transformations.map(product) {
        it?.colors
    }

    val sizes = Transformations.map(selectedColor) {
        it?.sizes
    }

    val shouldShowSize = Transformations.map(product) {
        it?.inStock == true
    }

    val sizeChart = Transformations.map(product) {
        it?.sizeChart
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

    fun addToCart() = cartRepository.addToCart(id, 1, attributeIds())

    fun isFavorite() = Transformations.map(repository.isFavorite(id)) {
        it?.isFavorite == true
    }

    fun favoriteToggle() = repository.favoriteToggle(id)

    fun seeAlsoFavoriteToggle(id: Int) = repository.seeAlsoFavoriteToggle(id)

    val VK_URL = Const.VK_URL
    val INSTAGRAM_URL = Const.INSTAGRAM_URL
    val TELEGRAM_URL = Const.TELEGRAM_URL
    val TIKTOK_URL = Const.TIKTOK_URL
    val FACEBOOK_URL = Const.FACEBOOK_URL

    fun setSelectedColor(item: ProductColor?) {
        val selectedSizeId = selectedSize.value?.id
        selectedSize.value = null

        item?.sizes?.forEach {
            if (selectedSizeId == it.id) {
                selectedSize.value = it
                return@forEach
            }
        }

        selectedColor.value = item
    }

    fun setSelectedSize(item: ProductSize?) {
        selectedSize.value = item
    }
}
