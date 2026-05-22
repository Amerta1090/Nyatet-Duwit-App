package com.nyatetduwit.presentation.transaction

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nyatetduwit.core.theme.NyatetDuwitColor
import com.nyatetduwit.core.theme.NyatetDuwitRadius
import com.nyatetduwit.core.theme.NyatetDuwitSpacing
import com.nyatetduwit.core.util.formatRupiah
import com.nyatetduwit.domain.model.Account
import com.nyatetduwit.domain.model.Category
import com.nyatetduwit.domain.model.Template
import com.nyatetduwit.domain.model.TransactionType
import com.nyatetduwit.presentation.components.CustomNumpad

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionFormScreen(
    transactionId: String? = null,
    onNavigateBack: () -> Unit,
    viewModel: TransactionViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val formState = uiState.formState
    val haptic = LocalHapticFeedback.current

    LaunchedEffect(transactionId) {
        transactionId?.let { viewModel.loadTransactionForEdit(it) }
    }

    LaunchedEffect(formState.isSuccess) {
        if (formState.isSuccess) {
            onNavigateBack()
            viewModel.clearSuccess()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (formState.type) {
                            TransactionType.INCOME -> "Pemasukan"
                            TransactionType.EXPENSE -> "Pengeluaran"
                            TransactionType.TRANSFER -> "Transfer"
                        },
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                ),
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            TransactionTypeSelector(
                selectedType = formState.type,
                onTypeSelected = { viewModel.setType(it) },
                modifier = Modifier.padding(horizontal = NyatetDuwitSpacing.lg),
            )

            if (uiState.templates.isNotEmpty()) {
                Spacer(modifier = Modifier.height(NyatetDuwitSpacing.md))
                TemplateStrip(
                    templates = uiState.templates,
                    onTemplateClick = { viewModel.applyTemplate(it) },
                    modifier = Modifier.padding(horizontal = NyatetDuwitSpacing.lg),
                )
            }

            Spacer(modifier = Modifier.height(NyatetDuwitSpacing.xxl))

            AmountHero(
                amount = formState.amount,
                modifier = Modifier.padding(horizontal = NyatetDuwitSpacing.lg),
            )

            Spacer(modifier = Modifier.height(NyatetDuwitSpacing.xl))

            CustomNumpad(
                onNumberClick = { viewModel.appendToAmount(it) },
                onBackspaceClick = { viewModel.backspaceAmount() },
                onPresetClick = { viewModel.setAmount(it) },
                currentAmount = formState.amount,
                modifier = Modifier.padding(horizontal = NyatetDuwitSpacing.sm),
            )

            Spacer(modifier = Modifier.weight(1f))

            BottomActionBar(
                amount = formState.amount,
                type = formState.type,
                selectedCategory = uiState.categories.find { it.id == formState.categoryId },
                selectedAccount = uiState.accounts.find { it.id == formState.accountId },
                selectedToAccount = uiState.accounts.find { it.id == formState.toAccountId },
                hasNotes = formState.notes != null,
                onSave = { viewModel.saveTransaction(transactionId) },
                onCategoryClick = { /* open category picker */ },
                onAccountClick = { /* open account picker */ },
                onNotesClick = { /* open notes input */ },
                isLoading = formState.isLoading,
            )
        }
    }

    if (formState.isError) {
        AlertDialog(
            onDismissRequest = { viewModel.clearError() },
            title = { Text("Error") },
            text = { Text(formState.errorMessage ?: "Terjadi kesalahan") },
            confirmButton = {
                TextButton(onClick = { viewModel.clearError() }) {
                    Text("OK")
                }
            },
        )
    }
}

@Composable
fun TransactionTypeSelector(
    selectedType: TransactionType,
    onTypeSelected: (TransactionType) -> Unit,
    modifier: Modifier = Modifier,
) {
    val haptic = LocalHapticFeedback.current

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.sm),
    ) {
        val types = listOf(
            TransactionType.EXPENSE to "Pengeluaran",
            TransactionType.INCOME to "Pemasukan",
            TransactionType.TRANSFER to "Transfer",
        )

        types.forEach { (type, label) ->
            val isSelected = type == selectedType
            val bgColor by animateColorAsState(
                targetValue = if (isSelected) when (type) {
                    TransactionType.EXPENSE -> NyatetDuwitColor.red100
                    TransactionType.INCOME -> NyatetDuwitColor.green100
                    TransactionType.TRANSFER -> NyatetDuwitColor.gold100
                } else Color.Transparent,
                animationSpec = spring(),
                label = "typeBg",
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(NyatetDuwitRadius.md))
                    .background(bgColor)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(),
                    ) {
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        onTypeSelected(type)
                    }
                    .padding(vertical = NyatetDuwitSpacing.md + 2.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = if (isSelected) when (type) {
                        TransactionType.EXPENSE -> NyatetDuwitColor.red500
                        TransactionType.INCOME -> NyatetDuwitColor.green500
                        TransactionType.TRANSFER -> NyatetDuwitColor.gold700
                    } else MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
fun AmountHero(
    amount: Long,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = formatRupiah(amount).ifEmpty { "Rp 0" },
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold,
            color = if (amount > 0) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
fun TemplateStrip(
    templates: List<Template>,
    onTemplateClick: (Template) -> Unit,
    modifier: Modifier = Modifier,
) {
    val haptic = LocalHapticFeedback.current

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.sm),
    ) {
        items(templates) { template ->
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(NyatetDuwitRadius.full))
                    .background(MaterialTheme.colorScheme.surface)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(),
                    ) {
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        onTemplateClick(template)
                    }
                    .padding(horizontal = NyatetDuwitSpacing.md, vertical = NyatetDuwitSpacing.sm),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.xs),
                ) {
                    Text(
                        text = when (template.type) {
                            TransactionType.INCOME -> "↓"
                            TransactionType.EXPENSE -> "↑"
                            TransactionType.TRANSFER -> "⇄"
                        },
                        style = MaterialTheme.typography.labelMedium,
                    )
                    Text(
                        text = template.name,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Medium,
                    )
                    Text(
                        text = formatRupiah(template.amount),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}

@Composable
private fun BottomActionBar(
    amount: Long,
    type: TransactionType,
    selectedCategory: Category?,
    selectedAccount: Account?,
    selectedToAccount: Account?,
    hasNotes: Boolean,
    isLoading: Boolean,
    onSave: () -> Unit,
    onCategoryClick: () -> Unit,
    onAccountClick: () -> Unit,
    onNotesClick: () -> Unit,
) {
    val haptic = LocalHapticFeedback.current
    val isEnabled = amount > 0

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = NyatetDuwitRadius.xl, topEnd = NyatetDuwitRadius.xl))
            .background(MaterialTheme.colorScheme.surface)
            .padding(NyatetDuwitSpacing.lg),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.sm),
        ) {
            if (type != TransactionType.TRANSFER) {
                PickerChip(
                    label = selectedCategory?.name ?: "Kategori",
                    icon = selectedCategory?.icon ?: "📁",
                    onClick = onCategoryClick,
                    modifier = Modifier.weight(1f),
                )
            }
            PickerChip(
                label = selectedAccount?.name ?: "Akun",
                icon = "💳",
                onClick = onAccountClick,
                modifier = Modifier.weight(1f),
            )
            PickerChip(
                label = if (hasNotes) "Ada catatan" else "Catatan",
                icon = if (hasNotes) "📝" else "✏️",
                onClick = onNotesClick,
                modifier = Modifier.weight(1f),
            )
        }

        Spacer(modifier = Modifier.height(NyatetDuwitSpacing.md))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .clip(RoundedCornerShape(NyatetDuwitRadius.md))
                .background(
                    if (isEnabled && !isLoading) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.surfaceVariant
                )
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(),
                    enabled = isEnabled && !isLoading,
                ) {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onSave()
                },
            contentAlignment = Alignment.Center,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(NyatetDuwitSpacing.sm),
            ) {
                if (!isLoading) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = if (isEnabled) MaterialTheme.colorScheme.onPrimary
                        else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp),
                    )
                }
                Text(
                    text = if (isLoading) "Menyimpan..." else "Simpan Transaksi",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = if (isEnabled && !isLoading) MaterialTheme.colorScheme.onPrimary
                    else MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun PickerChip(
    label: String,
    icon: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(NyatetDuwitRadius.md))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(),
            ) { onClick() }
            .padding(vertical = NyatetDuwitSpacing.sm),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = icon, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(NyatetDuwitSpacing.xxs))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
