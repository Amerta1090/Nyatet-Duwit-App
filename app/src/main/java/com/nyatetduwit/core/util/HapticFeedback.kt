package com.nyatetduwit.core.util

import android.content.Context
import android.os.Build
import android.view.HapticFeedbackConstants
import android.view.View

object HapticFeedback {

    fun click(view: View) {
        performHaptic(view, HapticFeedbackConstants.KEYBOARD_TAP)
    }

    fun success(view: View) {
        performHaptic(view, HapticFeedbackConstants.CONFIRM)
    }

    fun error(view: View) {
        performHaptic(view, HapticFeedbackConstants.REJECT)
    }

    fun longPress(view: View) {
        performHaptic(view, HapticFeedbackConstants.LONG_PRESS)
    }

    fun virtualKey(view: View) {
        performHaptic(view, HapticFeedbackConstants.VIRTUAL_KEY)
    }

    fun textHandleMove(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            view.performHapticFeedback(HapticFeedbackConstants.TEXT_HANDLE_MOVE)
        } else {
            view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
        }
    }

    private fun performHaptic(view: View, constant: Int) {
        view.performHapticFeedback(constant, HapticFeedbackConstants.FLAG_IGNORE_VIEW_SETTING)
    }
}
