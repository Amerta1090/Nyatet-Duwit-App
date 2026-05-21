package com.nyatetduwit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.nyatetduwit.core.navigation.NyatetDuwitNavHost
import com.nyatetduwit.core.security.AppLockManager
import com.nyatetduwit.core.theme.NyatetDuwitTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var appLockManager: AppLockManager

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

            NyatetDuwitTheme {
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
