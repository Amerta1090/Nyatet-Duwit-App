package com.nyatetduwit.core.security

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppLockManager(context: Context) : DefaultLifecycleObserver {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val _isLocked = MutableStateFlow(false)
    val isLocked: StateFlow<Boolean> = _isLocked.asStateFlow()

    private var backgroundTime: Long = 0
    private var isInBackground: Boolean = false

    init {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    fun getLockTimeoutMinutes(): Int {
        return prefs.getInt(KEY_LOCK_TIMEOUT, DEFAULT_LOCK_TIMEOUT)
    }

    fun setLockTimeoutMinutes(minutes: Int) {
        prefs.edit().putInt(KEY_LOCK_TIMEOUT, minutes).apply()
    }

    fun lock() {
        _isLocked.value = true
    }

    fun unlock() {
        _isLocked.value = false
        backgroundTime = 0
    }

    fun isLockRequired(): Boolean {
        val timeout = getLockTimeoutMinutes()
        if (timeout == 0) return true

        if (!isInBackground) return false

        val elapsedMinutes = (System.currentTimeMillis() - backgroundTime) / 60000
        return elapsedMinutes >= timeout
    }

    override fun onStop(owner: LifecycleOwner) {
        isInBackground = true
        backgroundTime = System.currentTimeMillis()
    }

    override fun onStart(owner: LifecycleOwner) {
        if (isLockRequired()) {
            _isLocked.value = true
        }
        isInBackground = false
    }

    companion object {
        private const val PREFS_NAME = "app_lock_prefs"
        private const val KEY_LOCK_TIMEOUT = "lock_timeout_minutes"
        private const val DEFAULT_LOCK_TIMEOUT = 5
    }
}
