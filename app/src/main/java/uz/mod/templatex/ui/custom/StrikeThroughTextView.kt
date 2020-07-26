package uz.mod.templatex.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class StrikeThroughTextView(context: Context, attrs: AttributeSet) : AppCompatTextView(context, attrs) {

    private val strikeLinePaint: Paint = with(Paint()) {
        color = Color.parseColor("#FFFF0000") // Red Color
        style = Paint.Style.FILL
        return@with this
    }

    private var rect: Rect? = null

    private fun getRect() : Rect {
        if (rect == null) rect = Rect(
                0,
                measuredHeight / 2 - 2,
                measuredWidth,
                measuredHeight / 2 + 2
            )
        return rect!!
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawStrikeThroughLine(canvas)
    }

    private fun drawStrikeThroughLine(canvas: Canvas?) = canvas?.apply {
        drawRect(getRect(), strikeLinePaint)
    }


}

