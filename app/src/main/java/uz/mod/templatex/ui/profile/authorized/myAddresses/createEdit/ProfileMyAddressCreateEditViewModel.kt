package uz.mod.templatex.ui.profile.authorized.myAddresses.createEdit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import uz.mod.templatex.model.local.entity.profile.ProfileRegion
import uz.mod.templatex.model.repository.profile.MyAddressesRepository
import uz.mod.templatex.ui.profile.authorized.myAddresses.ProfileMyAddressesFragment

class ProfileMyAddressCreateEditViewModel(
    application: Application,
    val repository: MyAddressesRepository
) :
    AndroidViewModel(application) {

    private val request = MutableLiveData<Boolean>()
    private val requestUpdate = MutableLiveData<Boolean>()
    private val requestStore = MutableLiveData<Boolean>()
    private var mode: ProfileMyAddressesFragment.Mode? = null
    private var addressId = -1
    var region: String? = null
    var selectedRegionId = -1
    val receiverName = MutableLiveData<String>()
    val address = MutableLiveData<String>()
    val city = MutableLiveData<String>()
    val postcode = MutableLiveData<String>()
    val phone = MutableLiveData<String>()
    val isPostalCodeExist = MutableLiveData<Boolean>()
    val isDefault = MutableLiveData<Boolean>()
    val allRegions = MutableLiveData<List<ProfileRegion>>()

    val response = Transformations.switchMap(request) {
        repository.getAddress(addressId)
    }
    val responseStore = Transformations.switchMap(requestStore) {
        var firstName: String? = null
        var lastName: String? = null
        var street: String? = null
        var building: String? = null
        var flat: String? = null
        var entry: String? = null

        receiverName.value?.split(" ")?.forEachIndexed { index, s ->
            when (index) {
                0 -> firstName = s.trim()
                1 -> lastName = s.trim()
                else -> lastName.plus(" $s")
            }
        }

        address.value?.split(",")?.forEachIndexed { index, s ->
            when (index) {
                0 -> street = s.trim()
                1 -> building = s.trim()
                2 -> flat = s.trim()
                3 -> entry = s.trim()
            }
        }

        repository.storeAddress(
            firstName,
            lastName,
            phone.value,
            city.value,
            street,
            building,
            flat,
            entry,
            if (isPostalCodeExist.value == true) postcode.value else null,
            selectedRegionId,
            isDefault.value
        )
    }
    val responseUpdate = Transformations.switchMap(requestUpdate) {
        var firstName: String? = null
        var lastName: String? = null
        var street: String? = null
        var building: String? = null
        var flat: String? = null
        var entry: String? = null

        receiverName.value?.trim()?.split(" ")?.forEachIndexed { index, s ->
            when (index) {
                0 -> firstName = s.trim()
                1 -> lastName = s.trim()
                else -> lastName.plus(" $s")
            }
        }

        address.value?.trim()?.split(",")?.forEachIndexed { index, s ->
            when (index) {
                0 -> street = s.trim()
                1 -> building = s.trim()
                2 -> flat = s.trim()
                3 -> entry = s.trim()
            }
        }
        val isDefault = if (isDefault.value == true) 1 else 0
        repository.updateAddress(
            addressId,
            firstName,
            lastName,
            phone.value,
            city.value,
            street,
            building,
            flat,
            entry,
            if (isPostalCodeExist.value == true) postcode.value else null,
            selectedRegionId,
            isDefault
        )
    }

    fun setArgs(args: ProfileMyAddressCreateEditFragmentArgs) {
        mode = args.mode
        addressId = args.addressId
        sendRequest()
    }

    fun sendRequest() {
        if (mode == ProfileMyAddressesFragment.Mode.EDIT) request.value = true
    }

    fun getCreateInfo() = repository.getCreateInfo()

    fun save() {
        when (mode) {
            ProfileMyAddressesFragment.Mode.CREATE -> requestStore.value = true
            ProfileMyAddressesFragment.Mode.EDIT -> requestUpdate.value = true
        }
    }

    /*fun updateAddress(
        firstName: String,
        lastName: String,
        phone: String,
        email: String,
        city: String,
        street: String,
        building: Int,
        flat: Int,
        entry: Int,
        postcode: Int,
        regionId: Int
    ) = repository.updateAddress(addressId,)*/
}