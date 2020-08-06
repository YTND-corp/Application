package uz.mod.templatex.model.inApp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import uz.mod.templatex.model.local.entity.Product

@Parcelize
data class CartResponseSafeArgs (
    var products : List<Product>
) : Parcelable