package uz.mod.templatex.utils

import android.os.Parcel
import android.os.Parcelable

open class Event<out T>(private val content: T) : Parcelable {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content

    override fun writeToParcel(parcel: Parcel, flags: Int) = Unit
    override fun describeContents() = 0
    companion object CREATOR : Parcelable.Creator<Event<Any>> {
        override fun createFromParcel(parcel: Parcel) = Event(parcel)
        override fun newArray(size: Int) = arrayOfNulls<Event<Any>>(size)
    }
}