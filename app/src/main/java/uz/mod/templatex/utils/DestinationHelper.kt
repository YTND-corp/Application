package uz.mod.templatex.utils

import androidx.navigation.NavDestination
import uz.mod.templatex.R

object DestinationHelper {

    fun getConfig(destination: NavDestination): DestinationConfig {
        return configs.find {
            it.id == destination.id
        } ?: DestinationConfig(0, true, true, true)
    }

    private val configs = setOf(
        DestinationConfig(R.id.selectionFragment, true, false, true, true),
        DestinationConfig(R.id.categoryFragment, false, false),
        DestinationConfig(R.id.favoriteFragment, false, false),
        DestinationConfig(R.id.profileFragment, true, false),
        DestinationConfig(
            R.id.profileAuthorizedFragment,
            true,
            false,
            toolbarGrayBackground = true
        ),
        DestinationConfig(R.id.profileMyOrdersFragment, true, true),
        DestinationConfig(R.id.cartFragment, false, false),
        DestinationConfig(R.id.productsFragment, false, false),
        DestinationConfig(R.id.productFragment, false, false),
        DestinationConfig(R.id.subCategoryFragment, true, true),

        DestinationConfig(R.id.checkoutFragment, bottomBar = false),
        DestinationConfig(R.id.addressFragment, bottomBar = false),
        DestinationConfig(R.id.codeFragment, bottomBar = false),
        DestinationConfig(R.id.deliveryFragment, bottomBar = false),
        DestinationConfig(R.id.paymentFragment, bottomBar = false),
        DestinationConfig(R.id.checkoutFinalFragment, false,false, bottomBar = false)
        DestinationConfig(R.id.subCategoryFragment, true, true),
        DestinationConfig(R.id.mainFilterFragment, false, true),
        DestinationConfig(R.id.singleAttributeFragment, false, true)
    )

    data class DestinationConfig(
        val id: Int,
        val toolbar: Boolean = true,
        val back: Boolean = true,
        val bottomBar: Boolean = true,
        val logo: Boolean = false,
        val isDialog: Boolean = false,
        val toolbarGrayBackground: Boolean = false
    )
}