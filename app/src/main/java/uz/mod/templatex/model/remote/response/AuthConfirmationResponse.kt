package uz.mod.templatex.model.remote.response

import uz.mod.templatex.model.local.entity.Meta
import uz.mod.templatex.model.local.entity.User

class AuthConfirmationResponse(
    val user: User,
    val meta: Meta
)