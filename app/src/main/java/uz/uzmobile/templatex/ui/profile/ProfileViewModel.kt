package uz.uzmobile.templatex.ui.profile

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import uz.uzmobile.templatex.R
import uz.uzmobile.templatex.extension.drawable
import uz.uzmobile.templatex.model.repository.AuthRepository

class ProfileViewModel constructor(application: Application, val authRepository: AuthRepository): AndroidViewModel(application) {
    private val context = application.applicationContext

    fun getUserName() = authRepository.getUserName()
    fun getUserPhone() = authRepository.getUserPhone()
}
