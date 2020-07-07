package uz.mod.templatex.analytics

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

/**
 * Sends custom analytics event/ Must be singleton or module of DI framework
 *
 * @author RamshkaAE
 */
class AnalyticsManager(context: Context) {
    private val firebaseAnalytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(context)

    /**
     * Sends custom event to analytics service
     */
    fun sendEvent(event: Bundle) {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, event)
    }
}