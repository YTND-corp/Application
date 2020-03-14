package uz.uzmobile.templatex.utils

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.preference.PreferenceManager
import java.util.*



enum class Language {
    uz, ru, en
}

object LanguageHelper {

    private val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"

    fun onAttach(context: Context): Context {
        val lang = getPersistedData(context)
        return setLocale(context, lang)

    }

    fun onAttach(context: Context, defaultLanguage: Language): Context {
        val lang = getPersistedData(context, defaultLanguage)
        return setLocale(context, lang)
    }

    fun getLanguage(context: Context): Language? {
        return getPersistedData(context)
    }

    fun setLocale(context: Context, language: Language?): Context {
        persist(context, language)

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(context, language)
        } else updateResourcesLegacy(context, language)

    }

    fun getLocale(): Locale {
        return  Locale.getDefault()
    }

    private fun getPersistedData(context: Context, defaultLanguage: Language = Language.ru): Language? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val lang = preferences.getString(SELECTED_LANGUAGE, defaultLanguage.name)
        if (lang == Language.uz.toString()) {
            return Language.uz
        } else if (lang == Language.en.toString()) {
            return Language.en
        } else {
            return Language.ru
        }
    }

    private fun persist(context: Context, language: Language?) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()

        editor.putString(SELECTED_LANGUAGE, language.toString())
        editor.apply()
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context, language: Language?): Context {
        val locale = Locale(language?.name)
        Locale.setDefault(locale)

        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)

        return context.createConfigurationContext(configuration)
    }

    private fun updateResourcesLegacy(context: Context, language: Language?): Context {
        val locale = Locale(language?.name)
        Locale.setDefault(locale)

        val resources = context.resources

        val configuration = resources.configuration
        configuration.setLayoutDirection(locale)
        return context
    }
}