package uz.mod.templatex.ui.profile.guest

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import uz.mod.templatex.model.repository.AuthRepository

class ProfileGuestViewModel constructor(
    application: Application,
    private val authRepository: AuthRepository
) : AndroidViewModel(application) {

    fun getUserName() = authRepository.getUserName()
    fun getUserPhone() = authRepository.getUserPhone()
}
