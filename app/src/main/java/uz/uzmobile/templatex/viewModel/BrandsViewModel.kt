package uz.uzmobile.templatex.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import timber.log.Timber
import uz.uzmobile.templatex.model.local.entity.Brand
import uz.uzmobile.templatex.model.remote.network.Resource
import uz.uzmobile.templatex.model.repository.ApiRepository

class BrandsViewModel constructor(application: Application, apiRepository: ApiRepository): AndroidViewModel(application) {

    private val userReq = MutableLiveData<Boolean>()
    val brands: LiveData<Resource<List<Brand>>> = Transformations.switchMap(userReq) { apiRepository.getBrands() }

    fun getBrands(){
        Timber.e("get brands....")
        userReq.value = true
    }

}
