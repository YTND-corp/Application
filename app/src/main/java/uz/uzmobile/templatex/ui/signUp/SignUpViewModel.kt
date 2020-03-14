package uz.uzmobile.templatex.ui.signUp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class SignUpViewModel constructor(application: Application): AndroidViewModel(application) {
    val phone = MutableLiveData<String>()

    fun onPasswordTextChanged(s: CharSequence,start: Int,before : Int, count :Int){
        Log.e("TAG", s.toString())
    }


}
