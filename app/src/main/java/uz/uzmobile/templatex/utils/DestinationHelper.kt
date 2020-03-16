package uz.uzmobile.templatex.utils

import androidx.navigation.NavDestination
import uz.uzmobile.templatex.R

object DestinationHelper {

    fun getConfig(destination: NavDestination): DestinationConfig {
        return configs.find {
            it.id == destination.id
        }?: DestinationConfig(0,false,false,false)
    }

    val configs = setOf(
        DestinationConfig(R.id.selectionFragment, true,false,true,true),
        DestinationConfig(R.id.catalogFragment, false,false,true),
        DestinationConfig(R.id.favoriteFragment, false,false,true),
        DestinationConfig(R.id.profileFragment, true,false,true),
        DestinationConfig(R.id.cartFragment, false,false,true),
        DestinationConfig(R.id.signInFragment, true,true,false),
        DestinationConfig(R.id.signUpFragment, true,true,false),
        DestinationConfig(R.id.recoveryPasswordFragment, true,true,false)
    )

    data class DestinationConfig(
        val id: Int,
        val toolbar: Boolean = true,
        val back: Boolean = true,
        val bottomBar: Boolean = true,
        val logo: Boolean = false
    )
}