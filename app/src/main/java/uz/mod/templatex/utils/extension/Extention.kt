package uz.mod.templatex.utils.extension

fun <T> lazyFast(initializer: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)
