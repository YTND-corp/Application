package uz.mod.templatex.app

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import androidx.multidex.MultiDex
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree
import uz.aqlify.yonda.utils.ProductionTree
import uz.mod.templatex.BuildConfig
import uz.mod.templatex.app.modules.appModules
import uz.mod.templatex.utils.AppSignatureHelper
import uz.mod.templatex.utils.LanguageHelper

class ModernApplication: Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LanguageHelper.onAttach(base))
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(DebugTree()) else Timber.plant(ProductionTree())

        val appSignature = AppSignatureHelper(this)
        appSignature.appSignatures

        startKoin {
            printLogger()
            androidContext(this@ModernApplication)
            modules(appModules)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        LanguageHelper.onAttach(this)
        super.onConfigurationChanged(newConfig)
    }
}