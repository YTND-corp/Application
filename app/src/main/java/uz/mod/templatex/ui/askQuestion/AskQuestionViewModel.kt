package uz.mod.templatex.ui.askQuestion

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import uz.mod.templatex.utils.Const

class AskQuestionViewModel  constructor(application: Application): AndroidViewModel(application) {
    val VK_URL = Const.VK_URL
    val INSTAGRAM_URL = Const.INSTAGRAM_URL
    val TELEGRAM_URL = Const.TELEGRAM_URL
    val TIKTOK_URL = Const.TIKTOK_URL
    val FACEBOOK_URL = Const.FACEBOOK_URL
}
