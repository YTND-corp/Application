package uz.uzmobile.templatex.utils

import androidx.navigation.NavDestination
import uz.uzmobile.templatex.R

object DestinationHelper {

    fun getConfig(destination: NavDestination): DestinationConfig {
        return configs.find {
            it.id == destination.id
        }?: DestinationConfig(0,true,true,true)
    }

    val configs = setOf(
        DestinationConfig(R.id.selectionFragment, true,false,true,true),
        DestinationConfig(R.id.categoryFragment, false,false),
        DestinationConfig(R.id.favoriteFragment, false,false),
        DestinationConfig(R.id.profileFragment, true,false),
        DestinationConfig(R.id.cartFragment, false,false),
        DestinationConfig(R.id.productsFragment, false,false),
        DestinationConfig(R.id.productFragment, false,false),
        DestinationConfig(R.id.subCategoryFragment, true,true)
    )

    data class DestinationConfig(
        val id: Int,
        val toolbar: Boolean = true,
        val back: Boolean = true,
        val bottomBar: Boolean = true,
        val logo: Boolean = false,
        val isDialog: Boolean = false
    )
}