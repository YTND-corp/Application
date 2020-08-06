package uz.mod.templatex.ui.payment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import uz.mod.templatex.model.remote.request.StoreRequest
import uz.mod.templatex.model.remote.response.PaymentMethod
import uz.mod.templatex.model.repository.CheckoutRepository
import uz.mod.templatex.utils.extension.moneyFormat

class PaymentViewModel constructor(application: Application, val repository: CheckoutRepository) :
    AndroidViewModel(application) {
    val selectedMethod = MutableLiveData<PaymentMethod>()

    val methods = MutableLiveData<List<PaymentMethod>>()

    val count = MutableLiveData<String>()
    val price = MutableLiveData<String>()
    val delivery = MutableLiveData<String>()
    val discount = MutableLiveData<String>()
    val total = MutableLiveData<String>()
    val netPrice = MutableLiveData<Int>()

    private var details: StoreRequest? = null

    var request = MutableLiveData<Boolean>()
    var response = Transformations.switchMap(request) {
        repository.store(
            details?.region?.id,
            details?.region?.name,
            details?.street,
            details?.home,
            details?.flat,
            details?.comment,
            details?.delivery?.carrierId,
            details?.delivery?.id ?: 1,
            details?.delivery?.date,
            details?.delivery?.transitTime,
            selectedMethod.value?.type,
            selectedMethod.value?.name
        )
    }

    fun setArguments(args: PaymentFragmentArgs) {

        args.response?.payment?.methods?.let {
            methods.value = it
        }

        selectedMethod.value = args.response?.payment?.methods?.first()

        val deliveryPrice = args.details?.delivery?.price ?: 0
        val productsTotalPrice = args.response?.payment?.cart?.totalPrice ?: 0
        val discountPrice = args.response?.payment?.cart?.discountPrice ?: 0

        netPrice.value = productsTotalPrice + deliveryPrice - discountPrice
        count.value = args.response?.payment?.cart?.quantity.toString()
        price.value = args.response?.payment?.cart?.productsPrice?.moneyFormat() + " UZS"
        delivery.value = deliveryPrice.moneyFormat() + " UZS"
        discount.value = discountPrice.moneyFormat() + " UZS"
        total.value = (productsTotalPrice + deliveryPrice - discountPrice).moneyFormat() + " UZS"

        details = args.details
    }

    fun buy() {
        request.value = true
    }

    val isPaymentMethodSelected = MediatorLiveData<Boolean>().apply {
        println("Checking point ${selectedMethod.value?.name}")
        fun validateFrom() {
            value = selectedMethod.value != null
        }
        addSource(selectedMethod) { validateFrom() }
    }

    fun setSelectedMethod(it: PaymentMethod) {
        selectedMethod.value = it
    }
}
