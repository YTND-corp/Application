package uz.uzmobile.templatex.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import uz.uzmobile.templatex.R


class MainViewModel constructor(application: Application) : AndroidViewModel(application) {

    val navDestination = MutableLiveData<NavDestination>()

    var title: LiveData<String> = Transformations.map(navDestination) { it.label.toString() }

    var isTitleVisible: LiveData<Boolean> = Transformations.map(navDestination) {
        when (it.id) {
            R.id.selectionFragment,
            R.id.catalogFragment,
            R.id.favoriteFragment,
            R.id.profileFragment,
            R.id.cartFragment -> false
            else -> true
        }
    }

    var isBottomBarVisible: LiveData<Boolean> = Transformations.map(navDestination) {
        when (it.id) {
            R.id.selectionFragment,
            R.id.catalogFragment,
            R.id.favoriteFragment,
            R.id.profileFragment,
            R.id.cartFragment -> true
            else -> false
        }
    }

    val listener =
        NavController.OnDestinationChangedListener { controller, destination, arguments ->
            navDestination.value = destination
        }
}
