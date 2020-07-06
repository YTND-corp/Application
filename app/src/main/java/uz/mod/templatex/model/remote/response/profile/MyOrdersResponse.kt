package uz.mod.templatex.model.remote.response.profile

import uz.mod.templatex.model.local.entity.profile.ProfileOrder

data class MyOrdersResponse(val orders: List<ProfileOrder>)