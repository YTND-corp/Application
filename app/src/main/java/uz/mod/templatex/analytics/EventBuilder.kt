package uz.mod.templatex.analytics

import android.content.Context
import android.os.Bundle
import com.google.firebase.crashlytics.internal.common.CommonUtils
import java.lang.RuntimeException

/**
 * Builds custom analytics events
 *
 * @author RamashkaAE
 */
class EventBuilder {

    private companion object {
        private const val EVENT_NAME_MIN_LENGTH = 1
        private const val EVENT_NAME_MAX_LENGTH = 40
        private const val EVENT_NAME_LENGTH_ERROR = "Event name length must be in range" +
                " [$EVENT_NAME_MIN_LENGTH,$EVENT_NAME_MAX_LENGTH]"

        private const val ROOT_FIELD_NAME = "IS_ROOTED"
    }

    private val eventFields = Bundle()
    private lateinit var eventName: String

    /**
     * Adds [eventName] name of analytics event to event info map
     */
    fun withEventName(eventName: String) = apply {
        if(eventName.length !in EVENT_NAME_MIN_LENGTH.. EVENT_NAME_MAX_LENGTH) {
            throw EventBuilderException(EVENT_NAME_LENGTH_ERROR)
        }
        this.eventName = eventName
    }


    /**
     * Adds custom event info field to event info map by [fieldName]
     */
    fun withEventField(fieldName: String, value: String) = apply {
        eventFields.putString(fieldName, value)
    }

    /**
     * Builds custom analytics event with additional info about root status and hardware device info optionally
     */
    fun build(context: Context, isRootInfoInclude: Boolean = true, isDeviceInfoInclude: Boolean = true): Bundle {
        if(isRootInfoInclude) {
            eventFields.putBoolean(ROOT_FIELD_NAME, CommonUtils.isRooted(context))
        }
        return eventFields
    }

    /**
     * Thrown when class protocol is violated
     */
    class EventBuilderException(message: String) : RuntimeException(message)
}