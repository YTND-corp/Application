package uz.mod.templatex.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class LockedViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {
    private var swipeEnabled = false

    override fun onTouchEvent(event: MotionEvent) =
        swipeEnabled && super.onTouchEvent(event)

    override fun onInterceptTouchEvent(event: MotionEvent) =
        swipeEnabled && super.onInterceptTouchEvent(event)

    fun setSwipePagingEnabled(swipeEnabled: Boolean) {
        this.swipeEnabled = swipeEnabled
    }
}