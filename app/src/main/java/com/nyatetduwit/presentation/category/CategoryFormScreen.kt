package com.nyatetduwit.presentation.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nyatetduwit.domain.model.CategoryType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryFormScreen(
    categoryId: String? = null,
    onNavigateBack: () -> Unit,
    viewModel: CategoryViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val formState = uiState.formState

    LaunchedEffect(categoryId) {
        viewModel.loadCategory(categoryId)
    }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onNavigateBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (categoryId == null) "Tambah Kategori" else "Edit Kategori") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            OutlinedTextField(
                value = formState.name,
                onValueChange = viewModel::onNameChange,
                label = { Text("Nama Kategori") },
                placeholder = { Text("Contoh: Makan Siang, Parkir") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )

            Text("Tipe Kategori", style = MaterialTheme.typography.labelLarge)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                CategoryType.entries.forEach { type ->
                    FilterChip(
                        selected = type == formState.type,
                        onClick = { viewModel.onTypeChange(type) },
                        label = { Text(if (type == CategoryType.EXPENSE) "Pengeluaran" else "Pemasukan") },
                        modifier = Modifier.weight(1f),
                    )
                }
            }

            Text("Icon", style = MaterialTheme.typography.labelLarge)
            CategoryIconSelector(
                selectedIcon = formState.icon,
                onIconSelected = viewModel::onIconChange,
            )

            Text("Warna", style = MaterialTheme.typography.labelLarge)
            ColorPicker(
                selectedColor = formState.color,
                onColorSelected = viewModel::onColorChange,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = viewModel::saveCategory,
                modifier = Modifier.fillMaxWidth(),
                enabled = formState.name.isNotBlank(),
            ) {
                Icon(Icons.Default.Save, null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (categoryId == null) "Simpan" else "Update")
            }
        }
    }

    uiState.error?.let { error ->
        AlertDialog(
            onDismissRequest = viewModel::clearError,
            title = { Text("Error") },
            text = { Text(error) },
            confirmButton = {
                TextButton(onClick = viewModel::clearError) {
                    Text("OK")
                }
            },
        )
    }
}

@Composable
private fun CategoryIconSelector(
    selectedIcon: String,
    onIconSelected: (String) -> Unit,
) {
    val icons = listOf(
        "restaurant" to Icons.Default.Restaurant,
        "directions_car" to Icons.Default.DirectionsCar,
        "shopping_bag" to Icons.Default.ShoppingBag,
        "receipt_long" to Icons.Default.ReceiptLong,
        "home" to Icons.Default.Home,
        "theater_comedy" to Icons.Default.TheaterComedy,
        "local_hospital" to Icons.Default.LocalHospital,
        "school" to Icons.Default.School,
        "checkroom" to Icons.Default.Checkroom,
        "card_giftcard" to Icons.Default.CardGiftcard,
        "payments" to Icons.Default.Payments,
        "work" to Icons.Default.Work,
        "trending_up" to Icons.Default.TrendingUp,
        "category" to Icons.Default.Category,
        "more_horiz" to Icons.Default.MoreHoriz,
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        icons.forEach { (name, icon) ->
            IconButton(
                onClick = { onIconSelected(name) },
                modifier = Modifier.size(44.dp),
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = name,
                    tint = if (name == selectedIcon)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun ColorPicker(
    selectedColor: String,
    onColorSelected: (String) -> Unit,
) {
    val colors = listOf(
        "#4F6B4E", "#42A5F5", "#FF7043", "#AB47BC",
        "#66BB6A", "#FFA726", "#EC407A", "#78909C",
        "#8D6E63", "#26A69A", "#7E57C2", "#EF5350",
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        colors.forEach { color ->
            val colorObj = try {
                Color(color.substring(1).toLong(16) or 0xFF000000L)
            } catch (e: Exception) {
                MaterialTheme.colorScheme.primary
            }

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clickable { onColorSelected(color) },
                contentAlignment = Alignment.Center,
            ) {
                Surface(
                    modifier = Modifier.size(36.dp),
                    color = colorObj,
                    shape = MaterialTheme.shapes.small,
                    tonalElevation = if (color == selectedColor) 4.dp else 0.dp,
                ) {}
                if (color == selectedColor) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier
                            .size(36.dp)
                            .padding(4.dp),
                        tint = Color.White,
                    )
                }
            }
        }
    }
}
