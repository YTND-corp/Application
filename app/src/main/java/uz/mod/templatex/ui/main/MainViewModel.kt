package uz.mod.templatex.ui.main

import android.app.Application
import androidx.lifecycle.*
import androidx.navigation.NavDestination
import uz.mod.templatex.model.repository.AuthRepository
import uz.mod.templatex.utils.DestinationHelper


class MainViewModel constructor(application: Application, val authRepository: AuthRepository) : AndroidViewModel(application) {

    val isAuthenticated = MutableLiveData<Boolean>(false)

    val destination = MutableLiveData<NavDestination>()

    var title = MutableLiveData<String>()

    var isToolbarVisible = MutableLiveData<Boolean>()

    var isLogoVisible = MutableLiveData<Boolean>()

    var hasBackButton = MutableLiveData<Boolean>()

    var hasBottomBar = MutableLiveData<Boolean>()

    var isBottomBarVisible = MediatorLiveData<Boolean>()

    var isKeyboardVisible = MutableLiveData<Boolean>()

    fun destinationChanged(destination: NavDestination) {
        val c = DestinationHelper.getConfig(destination)
        if (!c.isDialog) {
            this.destination.value = destination
            this.isToolbarVisible.value = c.toolbar
            this.hasBackButton.value = c.back
            this.title.value = destination.label?.toString()
            this.hasBottomBar.value = c.bottomBar
            this.isLogoVisible.value = c.logo
        }
    }

    fun keyboardVisibilityChanged(isVisible: Boolean) {
        isKeyboardVisible.value = isVisible
    }

    init {
        isAuthenticated.value = authRepository.isLoggedIn()

        isBottomBarVisible.addSource(hasBottomBar) {
            checkBottombar()
        }

        isBottomBarVisible.addSource(isKeyboardVisible) {
            checkBottombar()
        }

    }

    fun checkBottombar() {
        isBottomBarVisible.value = if (isKeyboardVisible.value == true) false
        else hasBottomBar.value == true
    }

    fun loggedIn() {
        isAuthenticated.value = true
    }

    fun loggedOut() {
        isAuthenticated.value = false
    }

    fun setTitle(name: String?) {
        title.value = name
    }
}
