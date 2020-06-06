package uz.uzmobile.templatex.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("isVisible")
fun isVisible(view: View, value: Boolean) {
    view.visibility = if (value) View.VISIBLE else View.GONE
}

@BindingAdapter("url")
fun url(view: ImageView, url: String? = null) {
    Glide.with(view.context)
        .load(url)//.apply(RequestOptions().circleCrop())
        .into(view)
}

@BindingAdapter("profileImage")
fun profileImage(view: ImageView, url: String? = null) {
    Glide.with(view.context)
        .load(url).apply(RequestOptions().circleCrop())
        .into(view)
}