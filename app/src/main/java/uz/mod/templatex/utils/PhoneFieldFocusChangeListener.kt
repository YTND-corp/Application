package uz.mod.templatex.utils

import android.view.View
import android.widget.EditText

class PhoneFieldFocusChangeListener() : View.OnFocusChangeListener {

    companion object {
        fun phoneWatcher(): PhoneFieldFocusChangeListener {
            return PhoneFieldFocusChangeListener()
        }
    }
    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        with(view as EditText) {
            if (hasFocus) {
                if (this.text?.length ?: 0 < 5 && this.text?.equals("+998") == false) {
                    this.setText("+998")
                }
            } else {
                if (this.text?.length ?: 0 <= 5) {
                    this.setText("")
                }
            }
        }
    }
}

