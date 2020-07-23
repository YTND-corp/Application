package uz.mod.templatex.ui.about

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import uz.mod.templatex.utils.Const

class AboutViewModel constructor(application: Application): AndroidViewModel(application) {
    val VK_URL = Const.VK_URL
    val INSTAGRAM_URL = Const.INSTAGRAM_URL
    val TELEGRAM_URL = Const.TELEGRAM_URL
    val TIKTOK_URL = Const.TIKTOK_URL
    val FACEBOOK_URL = Const.FACEBOOK_URL
    val PHONE_NUMBER = Const.PHONE_NUMBER
}

