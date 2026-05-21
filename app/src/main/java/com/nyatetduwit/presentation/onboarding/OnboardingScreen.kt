package com.nyatetduwit.presentation.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nyatetduwit.core.util.HapticFeedback
import kotlinx.coroutines.launch

data class OnboardingPage(
    val icon: ImageVector,
    val title: String,
    val description: String,
)

@Composable
fun OnboardingScreen(
    onComplete: () -> Unit,
    onSkip: () -> Unit,
) {
    val view = LocalView.current
    val scope = rememberCoroutineScope()
    val pages = listOf(
        OnboardingPage(
            icon = Icons.Default.AccountBalanceWallet,
            title = "Selamat Datang di NyatetDuwit",
            description = "Nyatet duit, hidup tenang. Aplikasi pencatat keuangan yang cepat, private, dan ringan.",
        ),
        OnboardingPage(
            icon = Icons.Default.EditNote,
            title = "Catat dalam 3 Detik",
            description = "Input transaksi super cepat dengan custom numpad dan smart suggestion. Gak perlu ribet!",
        ),
        OnboardingPage(
            icon = Icons.Default.PieChart,
            title = "Pahami Pola Keuanganmu",
            description = "Lihat ringkasan bulanan, budget progress, dan insight yang membantu kamu lebih aware.",
        ),
        OnboardingPage(
            icon = Icons.Default.Shield,
            title = "Aman & Private",
            description = "Data tersimpan di perangkat kamu. Offline-first, tanpa internet, tanpa sync yang ribet.",
        ),
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })
    var showAccountSetup by remember { mutableStateOf(false) }

    if (showAccountSetup) {
        AccountSetupStep(onComplete = onComplete, onBack = { showAccountSetup = false })
    } else {
        Scaffold(
            bottomBar = {
                OnboardingBottomBar(
                    currentPage = pagerState.currentPage,
                    pageCount = pages.size,
                    onNext = {
                        HapticFeedback.click(view)
                        if (pagerState.currentPage < pages.size - 1) {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        } else {
                            showAccountSetup = true
                        }
                    },
                    onSkip = {
                        HapticFeedback.click(view)
                        onSkip()
                    },
                    isLastPage = pagerState.currentPage == pages.size - 1,
                )
            },
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.weight(1f),
                ) { page ->
                    OnboardingPageContent(pages[page])
                }

                OnboardingPageIndicator(
                    currentPage = pagerState.currentPage,
                    pageCount = pages.size,
                    modifier = Modifier.padding(bottom = 16.dp),
                )
            }
        }
    }
}

@Composable
private fun OnboardingPageContent(page: OnboardingPage) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = page.icon,
            contentDescription = null,
            modifier = Modifier.size(120.dp),
            tint = MaterialTheme.colorScheme.primary,
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = page.title,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = page.description,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun OnboardingPageIndicator(
    currentPage: Int,
    pageCount: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
    ) {
        repeat(pageCount) { index ->
            Surface(
                modifier = Modifier
                    .width(if (index == currentPage) 24.dp else 8.dp)
                    .height(8.dp),
                shape = MaterialTheme.shapes.small,
                color = if (index == currentPage) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
            ) {}
        }
    }
}

@Composable
private fun OnboardingBottomBar(
    currentPage: Int,
    pageCount: Int,
    onNext: () -> Unit,
    onSkip: () -> Unit,
    isLastPage: Boolean,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (!isLastPage) {
            TextButton(onClick = onSkip) {
                Text("Lewati")
            }
        } else {
            Spacer(modifier = Modifier.width(64.dp))
        }

        Button(onClick = onNext) {
            Text(if (isLastPage) "Mulai" else "Lanjut")
            Icon(Icons.AutoMirrored.Filled.ArrowForward, null, modifier = Modifier.size(18.dp))
        }
    }
}
