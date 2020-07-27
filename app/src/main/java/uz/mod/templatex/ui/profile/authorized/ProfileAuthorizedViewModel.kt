package uz.mod.templatex.ui.profile.authorized

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import uz.mod.templatex.app.modules.repositoryModule
import uz.mod.templatex.model.local.entity.User
import uz.mod.templatex.model.repository.AuthRepository

class ProfileAuthorizedViewModel constructor(
    application: Application, val repository: AuthRepository
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
            gender = "",
            birthday = ""
        )
    }

    fun logout() {
        repository.logout()
    }
}
