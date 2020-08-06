package uz.mod.templatex.ui.paymentProvider

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import uz.mod.templatex.model.repository.CheckoutRepository
import uz.mod.templatex.model.repository.PaymentDetailsRepository
import java.util.*

class PaymentDetailsViewModel(private val repository: PaymentDetailsRepository, private val checkoutRepository: CheckoutRepository) : ViewModel() {


    private val request = MutableLiveData<Boolean>()
    private val storeRequest = MutableLiveData<Boolean>()
    private var args: PaymentDetailsFragmentArgs? = null

    private var lastRequestID = -1
    fun moneyAmount() = "${args?.paymentData?.amount} ${args?.paymentData?.currency}"
    val cardNumber = MutableLiveData<String>()
    val cardExpire = MutableLiveData<String>()
    val response = Transformations.switchMap(request) {
        lastRequestID = 0
        repository.pay(
            cardNumber.value!!,
            cardExpire.value!!,
            args?.paymentData?.amount!!,
            args?.paymentData?.provider?.name!!.toLowerCase(Locale.ROOT),
            args?.paymentData?.phone!!.replace(" ", "")
        )
    }

    val store = Transformations.switchMap(storeRequest) {
        lastRequestID = 1
        checkoutRepository.store(
            args?.details?.region?.id,
            args?.details?.region?.name,
            args?.details?.street,
            args?.details?.home,
            args?.details?.flat,
            args?.details?.comment,
            args?.details?.delivery?.carrierId,
            args?.details?.delivery?.id ?: 1,
            args?.details?.delivery?.date,
            args?.details?.delivery?.transitTime,
            args?.paymentData?.paymentMethod?.type,
            args?.paymentData?.provider?.name.toString().toLowerCase(Locale.ROOT)
        )
    }


    val isAllValid = MediatorLiveData<Boolean>().apply {
        fun isExpiryValid(value: String?): Boolean {
            value ?: return false
            return value.length == 4
        }

        fun validateFrom() {
            value = cardNumber.value?.length == 16
                    && isExpiryValid(cardExpire.value)
        }
        addSource(cardNumber) { validateFrom() }
        addSource(cardExpire) { validateFrom() }
    }

    fun setArguments(args: PaymentDetailsFragmentArgs) {
        this.args = args
    }

    fun pay() {
        request.value = true
    }

    fun store() {
        storeRequest.value = true
    }


    fun retryLastRequest() {
        when (lastRequestID) {
            0 -> pay()
            1 -> store()
        }
    }


}