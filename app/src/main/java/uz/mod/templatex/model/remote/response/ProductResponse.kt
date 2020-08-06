package uz.mod.templatex.model.remote.response

import com.google.gson.annotations.SerializedName
import uz.mod.templatex.model.local.entity.Currency
import uz.mod.templatex.model.local.entity.Product

data class ProductDetailResponse(
    @SerializedName("details") val product: ProductDetail,
    val delivery: ProductDelivery?,
    @SerializedName("similar_by_brand")
    val similarByBrand: List<Product>,
    @SerializedName("similar_by_category")
    val similarByCategory: List<Product>
)

data class ProductDetail(
    val id: Int,
    val name: String,
    val description: String?,
    @SerializedName("long_description") val longDescription: String?,
    val reference: String?,
    @SerializedName("is_prepaid") val isPrepaid: Boolean,
    @SerializedName("in_stock") val inStock: Boolean,
    @SerializedName("currency") val currencies: List<Currency>?,
    val colors: List<ProductColor>?,
    @SerializedName("is_favorites_added") val isFavorite: Boolean,
    val category: String?,
    val brand: String?,
    val features: List<ProductFeature>?,
    val images: List<String>?,
    @SerializedName("size_chart")
    val sizeChart: String?
) {
    fun priceFormatted() = currencies?.first()?.getMoneyFormat()

    fun oldPriceFormatted() = currencies?.first()?.getOldPriceFormat()

    fun compositionAndCare() = features?.joinToString("\n") { it.getText().toString() }
}

data class ProductDelivery(val date: String?, @SerializedName("return") val returnDate: String?)

data class ProductColor(
    val id: Int,
    val name: String?,
    val images: List<String>?,
    val color: String?,
    val reference: String?,
    val sizes: List<ProductSize>?
)

data class ProductSize(
    val id: Int,
    val name: String,
    val description: String,
    @SerializedName("in_stock")
    val inStock: Boolean,
    val currency: List<Currency>?
)

data class ProductFeature(val care: String?, val composition: String?) {
    fun getText() = care ?: composition
}

