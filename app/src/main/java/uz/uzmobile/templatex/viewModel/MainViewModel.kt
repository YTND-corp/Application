package uz.uzmobile.templatex.viewModel

import android.app.Application
import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener
import uz.uzmobile.templatex.R
import uz.uzmobile.templatex.utils.DestinationHelper


class MainViewModel constructor(application: Application) : AndroidViewModel(application) {

    private val destination = MutableLiveData<NavDestination>()

    var title: LiveData<String> = Transformations.map(destination) { it.label.toString() }

    var isToolbarVisible = MutableLiveData<Boolean>()

    var isLogoVisible = MutableLiveData<Boolean>()

    var hasBackButton = MutableLiveData<Boolean>()

    var hasBottomBar = MutableLiveData<Boolean>()

    var isBottomBarVisible = MediatorLiveData<Boolean>()

    var isKeyboardVisible = MutableLiveData<Boolean>()

    fun destinationChanged(destination: NavDestination) {
        this.destination.value = destination

        val c = DestinationHelper.getConfig(destination)
        this.isToolbarVisible.value = c.toolbar
        this.hasBackButton.value = c.back
        this.hasBottomBar.value = c.bottomBar
        this.isLogoVisible.value = c.logo
    }

    fun keyboardVisibilityChanged(isVisible: Boolean) {
        isKeyboardVisible.value = isVisible
    }

    init {
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
}
