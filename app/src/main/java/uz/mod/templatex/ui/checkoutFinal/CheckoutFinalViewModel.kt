package uz.mod.templatex.ui.checkoutFinal

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import uz.mod.templatex.R
import uz.mod.templatex.app.ModernApplication
import uz.mod.templatex.model.local.entity.Product
import uz.mod.templatex.model.repository.CheckoutRepository
import uz.mod.templatex.utils.extension.lazyFast
import uz.mod.templatex.utils.extension.moneyFormat
import java.util.*

class CheckoutFinalViewModel(application: Application, repository: CheckoutRepository) : AndroidViewModel(application) {


    val application by lazyFast { getApplication<ModernApplication>() }

    private var arguments: CheckoutFinalFragmentArgs? = null

    fun setArgs(args: CheckoutFinalFragmentArgs) {
        arguments = args
    }

    fun getProductSizeAndColor(product: Product): String {
        return "${product.combinations?.first { it.key == "Размер" }?.value}, ${product.combinations?.first {
            it.key?.toLowerCase(
                Locale.ROOT
            ) == "цвет"
        }?.value}"
    }

    fun getReference(product: Product): String {
        return "Код ${product.reference}"
    }

    fun getProductsQuantity(product: Product): String {
        return "${product.quantity} товар(ы)"
    }

    fun getProductsPriceRespectToQuantity(product: Product): String {
        return "${(product.quantity * (product.currencies?.first()?.getPrice() ?: 1))} ${product.currencies?.first()?.currency}"
    }


    fun getSuccessMessage() = String.format(
        application.getString(R.string.your_order_successfully_completed_text),
        arguments?.storeResponse?.order?.id.toString()
    )

    fun getAddress(): String {
        return arguments?.storeResponse?.address?.getAddressForSuccessPage() ?: "Unknown Address"
    }

    fun getDeliveryCost(): String {
        return arguments?.storeResponse?.cart?.deliveryPrice?.moneyFormat() + " UZS"
    }

    fun getDeliveryDiscount(): String {
        var discount = (arguments?.storeResponse?.order?.discount_price?.moneyFormat() ?: "0")
        if (discount != "0") discount = "-$discount"
        return "$discount UZS"
    }

    fun getTotalPrice(): String {
        val subTotal = arguments?.storeResponse?.order?.total_price ?: 0
        val deliveryCost = arguments?.storeResponse?.cart?.deliveryPrice ?: 0
        val deliveryDiscount = arguments?.storeResponse?.order?.discount_price ?: 0
        val total = subTotal + deliveryCost - deliveryDiscount
        return total.moneyFormat() + " UZS"
    }

    fun getDeliveryType(): String? {
        return arguments?.confirmResult?.delivery?.find {
            arguments?.storeResponse?.delivery?.carrierServiceID == it.id
        }?.name
    }
}
