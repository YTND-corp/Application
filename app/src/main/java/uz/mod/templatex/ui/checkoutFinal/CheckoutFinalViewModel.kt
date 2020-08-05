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

    fun getProductSize(product: Product): String {
        return "${product.combinations?.first { it.key == "Размер" }?.value}"
    }

    fun getColorAndReference(product: Product): String {
        return "${product.combinations?.first { it.key?.toLowerCase(Locale.ROOT) == "цвет" }?.value} - код ${product.reference}"
    }

    fun getProductsQuantity(product: Product): String {
        return "${product.quantity} товар(ы)"
    }

    fun getProductsPriceRespectToQuantity(product: Product): String {
        return "${(product.quantity * (product.currencies?.first()?.price ?: 1))} ${product.currencies?.first()?.currency}"
    }


    fun getSuccessMessage() = String.format(
        application.getString(R.string.your_order_successfully_completed_text),
        arguments?.storeResponse?.order?.id.toString()
    )

    fun getAddress(): String {
        return arguments?.storeResponse?.address?.getAddress() ?: "Unknown Address"
    }

    fun getDeliveryCost(): String {
        return arguments?.confirmResult?.delivery?.first()?.price?.moneyFormat() + " UZS"
    }

    fun getDeliveryDiscount(): String = "-" + (arguments?.storeResponse?.order?.discount_price?.moneyFormat() ?: "") + " UZS"

    fun getTotalPrice(): String  {
        val subTotal = arguments?.storeResponse?.order?.total_price ?:0
        val deliveryCost = arguments?.confirmResult?.delivery?.first()?.price ?:0
        val deliveryDiscount = arguments?.storeResponse?.order?.discount_price?:0
        val total = subTotal + deliveryCost - deliveryDiscount
        return total.moneyFormat() + " UZS"
    }

}
