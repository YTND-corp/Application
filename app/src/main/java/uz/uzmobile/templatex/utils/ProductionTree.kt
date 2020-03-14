package uz.aqlify.yonda.utils

import android.app.Application
import android.util.Log
import timber.log.Timber


class ProductionTree() : Timber.Tree() {


    init {
        // Initialize Fabric with Crashlytics.
//        Fabric.with(app, Crashlytics(), Answers())
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return
        }
////        Crashlytics.log(message)
//
//        // Log the exception in Crashlytics if we have one.
//        if (t != null) {
////            Crashlytics.logException(t)
//        }
//
//        // If this is an error or a warning, log it as a exception so we see it in Crashlytics.
//        if (priority > Log.WARN) {
////            Crashlytics.logException(Throwable(message))
//        }
//
//        // Track INFO level logs as custom Answers events.
//        if (priority == Log.INFO) {
////            Answers.getInstance().logCustom(CustomEvent(message))
//        }
    }
}