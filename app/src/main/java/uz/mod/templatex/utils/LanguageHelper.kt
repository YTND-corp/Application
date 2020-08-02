package uz.mod.templatex.utils

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.preference.PreferenceManager
import java.util.*



enum class Language {
    UZ, RU, EN
}

object LanguageHelper {

    private const val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"

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

    private fun getPersistedData(context: Context, defaultLanguage: Language = Language.RU): Language? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return when (preferences.getString(SELECTED_LANGUAGE, defaultLanguage.name)) {
            Language.UZ.toString() -> Language.UZ
            Language.EN.toString() -> Language.EN
            else -> Language.RU
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
        language ?: return context
        val locale = Locale(language.name)
        Locale.setDefault(locale)

        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)

        return context.createConfigurationContext(configuration)
    }

    private fun updateResourcesLegacy(context: Context, language: Language?): Context {
        language ?:return context
        val locale = Locale(language.name)
        Locale.setDefault(locale)

        val resources = context.resources

        val configuration = resources.configuration
        configuration.setLayoutDirection(locale)
        return context
    }
}