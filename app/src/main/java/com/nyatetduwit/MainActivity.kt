package com.nyatetduwit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.nyatetduwit.core.navigation.NyatetDuwitNavHost
import com.nyatetduwit.core.security.AppLockManager
import com.nyatetduwit.core.theme.NyatetDuwitTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val appLockManager: AppLockManager
        get() = (application as NyatetDuwitApp).appLockManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val app = (LocalContext.current.applicationContext as NyatetDuwitApp)
            val isOnboardingCompleted by app.settingsRepository.isOnboardingCompleted.collectAsState(initial = false)
            val isLocked by appLockManager.isLocked.collectAsState(initial = false)
            val navController = rememberNavController()
            val darkThemePref by app.settingsRepository.isDarkTheme.collectAsState(initial = false)
            val isExplicit by app.settingsRepository.isDarkThemeExplicit.collectAsState(initial = false)
            val accentColorPref by app.settingsRepository.accentColor.collectAsState(initial = "teal")
            val amoledPref by app.settingsRepository.isAmoledDark.collectAsState(initial = false)
            val finalDarkTheme = if (isExplicit) darkThemePref else isSystemInDarkTheme()

            NyatetDuwitTheme(darkTheme = finalDarkTheme, accentName = accentColorPref, amoledDark = amoledPref) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    if (isLocked) {
                        com.nyatetduwit.presentation.security.LockScreen(
                            onUnlock = { appLockManager.unlock() },
                            securityManager = app.securityManager,
                        )
                    } else {
                        NyatetDuwitNavHost(
                            navController = navController,
                            isOnboardingCompleted = isOnboardingCompleted,
                        )
                    }
                }
            }
        }
    }
}
