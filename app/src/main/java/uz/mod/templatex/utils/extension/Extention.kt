package uz.mod.templatex.utils.extension

import androidx.navigation.NavController




fun <T> lazyFast(initializer: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)
