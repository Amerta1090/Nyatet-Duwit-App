package com.nyatetduwit.presentation.account

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nyatetduwit.core.theme.NyatetDuwitRadius
import com.nyatetduwit.core.theme.NyatetDuwitSpacing
import com.nyatetduwit.domain.model.Account
import com.nyatetduwit.domain.model.AccountType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    onNavigateBack: (() -> Unit)? = null,
    onAddAccount: () -> Unit = {},
    onEditAccount: (String) -> Unit = {},
    viewModel: AccountViewModel = hiltViewModel(),
) {
    val accounts by viewModel.accounts.collectAsState()
    val totalBalance by viewModel.totalBalance.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    var showDeleteDialog by remember { mutableStateOf<Account?>(null) }
    val lazyListState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Akun") },
                navigationIcon = {
                    if (onNavigateBack != null) {
                        IconButton(onClick = onNavigateBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                        }
                    }
                },
                actions = {
                    IconButton(onClick = onAddAccount) {
                        Icon(Icons.Default.Add, "Add Account")
                    }
                },
            )
        },
    ) { paddingValues ->
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
            accounts.isEmpty() -> {
                EmptyAccountState(
                    modifier = Modifier.padding(paddingValues),
                    onAddAccount = onAddAccount,
                )
            }
            else -> {
            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .padding(paddingValues),
                contentPadding = PaddingValues(horizontal = NyatetDuwitSpacing.lg, vertical = NyatetDuwitSpacing.sm),
                verticalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.sm),
            ) {
                item {
                    TotalBalanceCard(totalBalance)
                    Spacer(modifier = Modifier.height(NyatetDuwitSpacing.sm))
                }
                items(accounts, key = { it.id }) { account ->
                    AccountItem(
                        account = account,
                        onEdit = { onEditAccount(account.id) },
                        onDelete = { showDeleteDialog = account },
                    )
                }
                }
            }
        }
    }

    showDeleteDialog?.let { account ->
        AlertDialog(
            onDismissRequest = { showDeleteDialog = null },
            title = { Text("Hapus Akun") },
            text = { Text("Yakin hapus \"${account.name}\"? Saldo awal akan hilang.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteAccount(account) { showDeleteDialog = null }
                    }
                ) {
                    Text("Hapus", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = null }) {
                    Text("Batal")
                }
            },
        )
    }
}

@Composable
private fun AccountItem(
    account: Account,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AccountItemContent(
        account = account,
        onEdit = onEdit,
        onDelete = onDelete,
        modifier = modifier,
    )
}

@Composable
private fun AccountItemContent(
    account: Account,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onEdit() },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(NyatetDuwitSpacing.lg),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = getAccountTypeIcon(account.type),
                contentDescription = null,
                modifier = Modifier.size(28.dp),
                tint = runCatching {
                    Color(account.color.substring(1).toLong(16) or 0xFF000000L)
                }.getOrElse { MaterialTheme.colorScheme.primary },
            )
            Spacer(modifier = Modifier.width(NyatetDuwitSpacing.md))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = account.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = getAccountTypeLabel(account.type),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Text(
                text = formatRupiah(account.balance),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.width(NyatetDuwitSpacing.sm))
            IconButton(onClick = onDelete) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(20.dp),
                )
            }
        }
    }
}

@Composable
private fun TotalBalanceCard(balance: Long) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        shape = RoundedCornerShape(NyatetDuwitRadius.md),
    ) {
        Column(
            modifier = Modifier.padding(NyatetDuwitSpacing.lg),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Total Saldo",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
            Spacer(modifier = Modifier.height(NyatetDuwitSpacing.xxs))
            Text(
                text = formatRupiah(balance),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }
    }
}

@Composable
private fun EmptyAccountState(
    modifier: Modifier = Modifier,
    onAddAccount: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(NyatetDuwitSpacing.xxxl),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(NyatetDuwitRadius.xl))
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                Icons.Default.AccountBalanceWallet,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Spacer(modifier = Modifier.height(NyatetDuwitSpacing.lg))
        Text(
            text = "Belum ada akun",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(NyatetDuwitSpacing.sm))
        Text(
            text = "Yuk mulai nyatet! Tambah akun pertama kamu.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.height(NyatetDuwitSpacing.xxl))
        Button(
            onClick = onAddAccount,
            shape = RoundedCornerShape(NyatetDuwitRadius.md),
        ) {
            Icon(Icons.Default.Add, null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(NyatetDuwitSpacing.sm))
            Text("Tambah Akun")
        }
    }
}

internal fun getAccountTypeIcon(type: AccountType) = when (type) {
    AccountType.CASH -> Icons.Default.Payments
    AccountType.BANK -> Icons.Default.AccountBalance
    AccountType.E_WALLET -> Icons.Default.PhoneAndroid
}

internal fun getAccountTypeLabel(type: AccountType) = when (type) {
    AccountType.CASH -> "Cash"
    AccountType.BANK -> "Bank"
    AccountType.E_WALLET -> "E-Wallet"
}

fun formatRupiah(amount: Long): String {
    val absAmount = kotlin.math.abs(amount)
    val formatted = String.format("%,d", absAmount).replace(',', '.')
    return if (amount < 0) "-Rp $formatted" else "Rp $formatted"
}
