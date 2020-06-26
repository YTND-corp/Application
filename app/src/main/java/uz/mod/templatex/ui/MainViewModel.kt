package uz.mod.templatex.ui

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

    var toolbarGrayBackground = MutableLiveData<Boolean>()

    var isLogoVisible = MutableLiveData<Boolean>()

    var hasBackButton = MutableLiveData<Boolean>()

    var hasBottomBar = MutableLiveData<Boolean>()

    var isKeyboardVisible = MutableLiveData<Boolean>()

    fun destinationChanged(destination: NavDestination) {
        val config = DestinationHelper.getConfig(destination)
        if (!config.isDialog) {
            this.destination.value = destination
            this.isToolbarVisible.value = config.toolbar
            this.hasBackButton.value = config.back
            this.title.value = destination.label?.toString()
            this.hasBottomBar.value = config.bottomBar
            this.isLogoVisible.value = config.logo
            this.toolbarGrayBackground.value = config.toolbarGrayBackground
        }
    }

    fun keyboardVisibilityChanged(isVisible: Boolean) {
        isKeyboardVisible.value = isVisible
    }

    init {
        isAuthenticated.value = authRepository.isLoggedIn()
    }

    val isBottomBarVisible = MediatorLiveData<Boolean>()
        .apply {
            fun validateFrom() {
                value = if(isKeyboardVisible.value == true) false else hasBottomBar.value == true
            }

            addSource(isKeyboardVisible) { validateFrom() }
            addSource(hasBottomBar) { validateFrom() }
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
