package uz.aqlify.yonda.utils

import android.content.Context
import android.content.SharedPreferences
import timber.log.Timber
import uz.uzmobile.templatex.utils.Language

class Prefs constructor(context: Context) {
    private val NAME = "Yonda_Application"
    private val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    // list of app specific preferences
//    private val HAS_NAME = "hasName"
    private val IS_VERIFIED = "isVerified"
    private val HAS_NAME = "hasName"
    private val HAS_CAR = "hasCar"
    private val TOKEN = "token"
    private val FIREBASE_TOKEN = "firebaseToken"
    private val IS_TOKEN_SENT = "isTokenSent"
    private val PHONE = "phone"
    private val IMAGE = "image"
    private val SELECTED_LANGUAGE = "selected_language"

    init {
        preferences = context.getSharedPreferences(
            NAME,
            MODE
        )
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }


    var isVerified: Boolean
        get() = preferences.getBoolean(IS_VERIFIED, false)
        set(value) = preferences.edit {
            it.putBoolean(IS_VERIFIED, value)
        }

    var hasName: Boolean
        get() = preferences.getBoolean(HAS_NAME, false)
        set(value) = preferences.edit {
            it.putBoolean(HAS_NAME, value)
        }

    var hasCar: Boolean
        get() = preferences.getBoolean(HAS_CAR, false)
        set(value) = preferences.edit {
            it.putBoolean(HAS_CAR, value)
        }

    var token: String?
        get() = preferences.getString(TOKEN, null)
        set(value) = preferences.edit {
            it.putString(TOKEN, value)
        }

    var firebaseToken: String?
        get() = preferences.getString(FIREBASE_TOKEN, null)
        set(value) = preferences.edit {
            it.putString(FIREBASE_TOKEN, value)
        }

    var isTokenSent: Boolean
        get() = preferences.getBoolean(IS_TOKEN_SENT, false)
        set(value) = preferences.edit {
            it.putBoolean(IS_TOKEN_SENT, value)
        }

    var phone: String?
        get() = preferences.getString(PHONE, null)
        set(value) = preferences.edit {
            it.putString(PHONE, value)
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
        isVerified = false
        hasCar = false
        hasName = false
        token = null
        phone = null
        Timber.e("hasName = $hasName")
    }
}