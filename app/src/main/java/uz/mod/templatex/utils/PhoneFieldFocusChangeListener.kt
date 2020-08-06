package uz.mod.templatex.utils

import android.view.View
import android.widget.EditText

class PhoneFieldFocusChangeListener : View.OnFocusChangeListener {

    companion object {
        fun phoneWatcher(): PhoneFieldFocusChangeListener {
            return PhoneFieldFocusChangeListener()
        }
    }
    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        with(view as EditText) {
            /*if (hasFocus) {
                if (this.text?.length ?: 0 < 5 && this.text?.equals(Const.PHONE_CODE_DEFAULT) == false) {
                    this.setText(Const.PHONE_CODE_DEFAULT)
                }
            } else {
                if (this.text?.length ?: 0 <= 5) {
                    this.setText("")
                }
            }*/

            if (hasFocus && text.isBlank()) {
                setText(Const.PHONE_CODE_DEFAULT)
                setSelection(text.length)
            }
        }
    }
}

