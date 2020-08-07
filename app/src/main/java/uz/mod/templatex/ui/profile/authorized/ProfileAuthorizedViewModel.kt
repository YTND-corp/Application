package uz.mod.templatex.ui.profile.authorized

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import uz.mod.templatex.model.repository.AuthRepository

class ProfileAuthorizedViewModel constructor(
    application: Application,
    val repository: AuthRepository
) : AndroidViewModel(application) {

    fun getUserName() = repository.getUserName()

    fun logout() {
        repository.logout()
    }
}
