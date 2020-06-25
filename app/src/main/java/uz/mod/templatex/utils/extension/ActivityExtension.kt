package uz.mod.templatex.utils.extension

import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


fun AppCompatActivity.toast(resourceId: Int, isShortToast: Boolean = true) {
    if (isShortToast)
        Toast.makeText(this, resourceId, Toast.LENGTH_SHORT).show()
    else
        Toast.makeText(this, resourceId, Toast.LENGTH_LONG).show()

}


fun AppCompatActivity.toast(message: String, isShortToast: Boolean = true) {
    if (isShortToast)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    else
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

}

fun AppCompatActivity.color(resourceId: Int): Int {
    return ContextCompat.getColor(this, resourceId)
}

fun AppCompatActivity.drawable(resourceId: Int): Drawable? {
    return ContextCompat.getDrawable(this, resourceId)
}
