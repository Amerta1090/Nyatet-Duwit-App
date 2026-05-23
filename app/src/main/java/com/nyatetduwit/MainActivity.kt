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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.nyatetduwit.core.navigation.NyatetDuwitNavHost
import com.nyatetduwit.core.security.AppLockManager
import com.nyatetduwit.core.theme.NyatetDuwitTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val appLockManager: AppLockManager
        get() = (application as NyatetDuwitApp).appLockManager

    private var isOnboardingCompleted: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lifecycleScope.launch {
            val settingsRepository = (application as NyatetDuwitApp).settingsRepository
            isOnboardingCompleted = settingsRepository.isOnboardingCompleted.first()
        }

        setContent {
            val isLocked by appLockManager.isLocked.collectAsState(initial = false)
            val navController = rememberNavController()
            val context = LocalContext.current
            val app = context.applicationContext as NyatetDuwitApp
            val darkThemePref by app.settingsRepository.isDarkTheme.collectAsState(initial = false)
            val isExplicit by app.settingsRepository.isDarkThemeExplicit.collectAsState(initial = false)
            val finalDarkTheme = if (isExplicit) darkThemePref else isSystemInDarkTheme()

            NyatetDuwitTheme(darkTheme = finalDarkTheme) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    if (isLocked) {
                        com.nyatetduwit.presentation.security.LockScreen(
                            onUnlock = { appLockManager.unlock() },
                            securityManager = (application as NyatetDuwitApp).securityManager,
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
