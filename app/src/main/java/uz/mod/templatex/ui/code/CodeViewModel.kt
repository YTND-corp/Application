package uz.mod.templatex.ui.code

import android.app.Application
import androidx.lifecycle.*
import uz.mod.templatex.R
import uz.mod.templatex.model.remote.network.Resource
import uz.mod.templatex.model.remote.response.ConfirmResponse
import uz.mod.templatex.model.repository.CheckoutRepository
import uz.mod.templatex.utils.extension.backEndPhoneFormat

class CodeViewModel constructor(application: Application, val repository: CheckoutRepository) :
    AndroidViewModel(application) {

    val context = application.applicationContext
    val code = MutableLiveData<String>()
    val phone = MutableLiveData<String>()

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

    val request = MutableLiveData<Boolean>()
    val responce: LiveData<Resource<ConfirmResponse>> = Transformations.switchMap(request) {
        repository.confirm(
            phone.value?.backEndPhoneFormat() ?: "",
            code.value!!
        )
    }



    fun checkCode() {

    }

    fun setArguments(args: CodeFragmentArgs) {
        phone.value = args.request?.phone
    }

    fun confirm() {
        request.value = true
    }

    var getSubTitle = Transformations.map(phone) {
        application.getString(R.string.code_subheader, it)
    }
}