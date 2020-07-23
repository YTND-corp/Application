package uz.mod.templatex.ui.code

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import uz.mod.templatex.R
import uz.mod.templatex.model.local.entity.User
import uz.mod.templatex.model.remote.network.Resource
import uz.mod.templatex.model.remote.response.ConfirmResponse
import uz.mod.templatex.model.repository.AuthRepository
import uz.mod.templatex.model.repository.CheckoutRepository
import uz.mod.templatex.utils.extension.backEndPhoneFormat

class CodeViewModel constructor(
    application: Application,
    val repository: CheckoutRepository,
    val authRepository: AuthRepository
) :
    AndroidViewModel(application) {

    val context = application.applicationContext
    val code = MutableLiveData<String>()
    val phone = MutableLiveData<String>()
    var isCheckout = false

    val first = Transformations.map(code) {
        if (it.isNotEmpty()) it[0].toString() else ""
    }
    val second = Transformations.map(code) {
        if (it.length >= 2) it[1].toString() else ""
    }
    val third = Transformations.map(code) {
        if (it.length >= 3) it[2].toString() else ""
    }
    val fourth = Transformations.map(code) {
        if (it.length >= 4) it[3].toString() else ""
    }

    val authConfirmRequest = MutableLiveData<Boolean>()
    val checkoutConfirmRequest = MutableLiveData<Boolean>()
    val authConfirmResponse: LiveData<Resource<User>> = Transformations.switchMap(authConfirmRequest) {
        authRepository.confirm(code.value!!)
    }
    val checkoutConfirmResponse: LiveData<Resource<ConfirmResponse>> = Transformations.switchMap(checkoutConfirmRequest) {
        repository.confirm(
            phone.value?.backEndPhoneFormat() ?: "",
            code.value!!
        )
    }


    fun checkCode() {

    }

    fun setArguments(args: CodeFragmentArgs) {
        phone.value = args.phone
    }

    fun checkoutConfirm() {
        checkoutConfirmRequest.value = true
    }

    fun authConfirm() {
        authConfirmRequest.value = true
    }

    var getSubTitle = Transformations.map(phone) {
        application.getString(R.string.code_subheader, it)
    }
}