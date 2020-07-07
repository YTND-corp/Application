package uz.mod.templatex.model.remote.response

import com.google.gson.annotations.SerializedName
import uz.mod.templatex.model.local.entity.City

data class CheckoutResponse(@SerializedName("regions") val cities: List<City>)