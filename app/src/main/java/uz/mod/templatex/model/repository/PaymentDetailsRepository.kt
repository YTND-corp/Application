package uz.mod.templatex.model.repository

import androidx.lifecycle.LiveData
import uz.mod.templatex.model.remote.api.PaymentService
import uz.mod.templatex.model.remote.network.NetworkOnlyResource
import uz.mod.templatex.model.remote.network.Resource
import uz.mod.templatex.model.remote.response.PaymentResponse

class PaymentDetailsRepository(private val paymentService: PaymentService) {

    fun pay(
        cardNumber: String,
        cardExpireDate: String,
        moneyAmount: Int,
        paymentProvider: String,
        phone: String
    ): LiveData<Resource<PaymentResponse>> = object : NetworkOnlyResource<PaymentResponse, PaymentResponse>() {
        override fun processResult(item: PaymentResponse?) = item
        override fun createCall() =  paymentService.pay(cardNumber, cardExpireDate, moneyAmount, paymentProvider, phone)
    }.asLiveData()
}