package uz.mod.templatex.model.inApp

import androidx.annotation.IdRes

data class BottomNavCustomSelectionArgs(
    @IdRes val destGraphID: Int,
    @IdRes val destFragmentID: Int
)