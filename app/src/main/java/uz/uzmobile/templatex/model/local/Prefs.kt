package uz.aqlify.yonda.utils

import android.content.Context
import android.content.SharedPreferences
import timber.log.Timber
import uz.uzmobile.templatex.model.local.entity.User
import uz.uzmobile.templatex.utils.Language

class Prefs constructor(context: Context) {
    private val APP_NAME = "Yonda_Application"
    private val MODE = Context.MODE_PRIVATE
    private var preferences: SharedPreferences

    // list of app specific preferences
//    private val HAS_NAME = "hasName"
    private val IS_VERIFIED = "isVerified"
    private val USER_NAME = "hasName"
    private val TOKEN = "token"
    private val PHONE = "phone"
    private val IMAGE = "image"
    private val SELECTED_LANGUAGE = "selected_language"

    init {
        preferences = context.getSharedPreferences(
            APP_NAME,
            MODE
        )
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var token: String?
        get() = preferences.getString(TOKEN, null)
        set(value) = preferences.edit {
            it.putString(TOKEN, value)
        }

    var phone: String?
        get() = preferences.getString(PHONE, null)
        set(value) = preferences.edit {
            it.putString(PHONE, value)
        }

    var userName: String?
        get() = preferences.getString(USER_NAME, null)
        set(value) = preferences.edit {
            it.putString(USER_NAME, value)
        }

    var selectedLanguage: Language?
        get() {
            val selected = preferences.getString(SELECTED_LANGUAGE,Language.ru.name)
            if (selected == Language.uz.toString()) {
                return Language.uz
            } else if (selected == Language.en.toString()) {
                return Language.en
            } else {
                return Language.ru
            }
        }
        set(value) = preferences.edit {
            it.putString(IMAGE, value?.name)
        }

    fun logOut() {
        token = null
        phone = null
    }
}