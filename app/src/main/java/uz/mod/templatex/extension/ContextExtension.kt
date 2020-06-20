package uz.mod.templatex.extension

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat

fun Context.toast(resourceId: Int, isShortToast: Boolean = true) {
    if (isShortToast)
        Toast.makeText(this, resourceId, Toast.LENGTH_SHORT).show()
    else
        Toast.makeText(this, resourceId, Toast.LENGTH_LONG).show()

}


fun Context.toast(message: String, isShortToast: Boolean = true) {
    if (isShortToast)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    else
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

}

fun Context.color(resourceId: Int): Int {
    return ContextCompat.getColor(this, resourceId)
}

fun Context.drawable(resourceId: Int): Drawable? {
    return ContextCompat.getDrawable(this, resourceId)
}

val Context.inputMethodManager
    get() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
