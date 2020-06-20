package uz.mod.templatex.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import uz.mod.templatex.model.repository.AuthRepository

class ProfileViewModel constructor(application: Application, val authRepository: AuthRepository): AndroidViewModel(application) {
    private val context = application.applicationContext

    fun getUserName() = authRepository.getUserName()
    fun getUserPhone() = authRepository.getUserPhone()
}
