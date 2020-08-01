package uz.mod.templatex.ui.delivery

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import uz.mod.templatex.model.remote.response.Delivery

class DeliveryViewModel constructor(application: Application): AndroidViewModel(application)  {

    val selectedOption = MutableLiveData<Delivery>()
    val options = MutableLiveData<List<Delivery>>()

    /*fun setArguments(args: DeliveryFragmentArgs) {
        options.value = args.response?.delivery
        selectedOption.value = args.response?.delivery?.firstOrNull()
    }*/

    fun setSelected(delivery: Delivery) {
        selectedOption.value = delivery
    }
}
