package uz.mod.templatex.ui.brands

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import timber.log.Timber
import uz.mod.templatex.model.local.entity.Brand
import uz.mod.templatex.model.remote.network.Resource
import uz.mod.templatex.model.repository.ApiRepository

class BrandsViewModel constructor(application: Application, apiRepository: ApiRepository): AndroidViewModel(application) {

    private val userReq = MutableLiveData<Boolean>()
    val brands: LiveData<Resource<List<Brand>>> = Transformations.switchMap(userReq) { apiRepository.getBrands() }

    fun getBrands(){
        Timber.e("get brands....")
        userReq.value = true
    }

}
