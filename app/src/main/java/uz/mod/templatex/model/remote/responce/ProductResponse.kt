package uz.mod.templatex.model.remote.responce

import com.google.gson.annotations.SerializedName
import uz.mod.templatex.extension.moneyFormat
import uz.mod.templatex.model.local.entity.Brand
import uz.mod.templatex.model.local.entity.Category
import uz.mod.templatex.model.local.entity.Currency
import kotlin.collections.ArrayList

data class ProductDetailResponse(val product: ProductDetail, val delivery: ProductDelivery?)

data class ProductDetail(
    val id: Int,
    val name: String,
    val description: String,
    @SerializedName("is_prepaid") val isPrepaid: Boolean,
    @SerializedName("in_stock") val inStock: Boolean,
    val price: Int,
    val currencies: List<Currency>?,
    val attributeCombination: AttributeCombination?,
    @SerializedName("isFavoritesAdded") val isFavorite: Boolean,
    val category: Category?,
    val brand: Brand?,
    @SerializedName("product_features") val features: List<ProductFeature>?,
    val image: List<String>?
) {
    fun priceFormatted() = price.moneyFormat() + " " + currencies?.first()?.currency

    fun compositionAndCare(): String? {
        var temps = ArrayList<String>()
        features?.forEach { feature ->
            feature.feature_values?.joinToString (separator = ", ", postfix = " "){
                it.value.name
            }?.let {
                temps.add(it)
            }

        }
        return if (temps.isEmpty()) null else temps.joinToString (separator = "\n")
    }
}

data class ProductDelivery(val date: String?, @SerializedName("return") val returnDate: String?)

data class AttributeCombination(
    @SerializedName("tsvet") val colors: List<ProductColor>?,
    @SerializedName("razmer") val sizes: List<ProductSize>?
)

data class ProductColor(
    val id: Int,
    val name: String?,
    val color: String?,
    @SerializedName("attribute_id") val attributeId: Int,
    @SerializedName("sort_order") val order: Int
)

data class ProductSize(
    val id: Int,
    val name: String?,
    val color: String?,
    @SerializedName("attribute_id") val attributeId: Int,
    @SerializedName("sort_order") val order: Int
)

data class ProductFeature(val feature_values: List<FeatureValueWrapper>?)
data class FeatureValueWrapper(val value: FeatureValue)
data class FeatureValue(val name: String)
