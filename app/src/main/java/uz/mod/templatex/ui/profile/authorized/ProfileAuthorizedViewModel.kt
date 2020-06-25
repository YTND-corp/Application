package uz.mod.templatex.ui.profile.authorized

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import uz.mod.templatex.model.local.entity.User

class ProfileAuthorizedViewModel constructor(
    application: Application
) : AndroidViewModel(application) {

    fun getUser(): User {
        // TODO should be api call or data from authorization
        return User(
            name = "Alexander",
            id = 0,
            surname = "",
            email = "",
            country = "",
            phone = "",
            expiresIn = 0L,
            token = "",
            tokenType = ""
        )
    }
}
