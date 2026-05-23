package com.nyatetduwit.presentation.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nyatetduwit.domain.model.Category
import com.nyatetduwit.domain.model.CategoryType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    onNavigateBack: () -> Unit,
    onAddCategory: () -> Unit,
    onEditCategory: (String) -> Unit,
    viewModel: CategoryViewModel = hiltViewModel(),
) {
    val expenseCategories by viewModel.expenseCategories.collectAsState()
    val incomeCategories by viewModel.incomeCategories.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    var showDeleteDialog by remember { mutableStateOf<Category?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Kategori") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onAddCategory) {
                        Icon(Icons.Default.Add, "Add Category")
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
            expenseCategories.isEmpty() && incomeCategories.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "Belum ada kategori",
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }
            else -> {
                LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (expenseCategories.isNotEmpty()) {
                item {
                    Text(
                        text = "Pengeluaran",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(vertical = 8.dp),
                    )
                }
                items(expenseCategories, key = { it.id }) { category ->
                    CategoryItem(
                        category = category,
                        onEdit = { onEditCategory(category.id) },
                        onDelete = { showDeleteDialog = category },
                    )
                }
            }

            if (incomeCategories.isNotEmpty()) {
                item {
                    Text(
                        text = "Pemasukan",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(vertical = 8.dp),
                    )
                }
                items(incomeCategories, key = { it.id }) { category ->
                    CategoryItem(
                        category = category,
                        onEdit = { onEditCategory(category.id) },
                        onDelete = { showDeleteDialog = category },
                    )
                }
            }
                }
            }
        }
    }

    showDeleteDialog?.let { category ->
        AlertDialog(
            onDismissRequest = { showDeleteDialog = null },
            title = { Text("Hapus Kategori") },
            text = { Text("Yakin hapus \"${category.name}\"?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteCategory(category) { showDeleteDialog = null }
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
private fun CategoryItem(
    category: Category,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onEdit() },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val color = try {
                Color(category.color.substring(1).toLong(16) or 0xFF000000)
            } catch (e: Exception) {
                MaterialTheme.colorScheme.primary
            }

            Surface(
                modifier = Modifier.size(40.dp),
                shape = MaterialTheme.shapes.small,
                color = color.copy(alpha = 0.2f),
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = getCategoryIcon(category.icon),
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier.size(24.dp),
                    )
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = category.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            if (!category.isDefault) {
                IconButton(onClick = onDelete) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error,
                    )
                }
            } else {
                Icon(
                    Icons.Default.Lock,
                    contentDescription = "Default",
                    tint = MaterialTheme.colorScheme.outlineVariant,
                    modifier = Modifier.size(20.dp),
                )
            }
        }
    }
}

private fun getCategoryIcon(iconName: String) = when (iconName) {
    "restaurant" -> Icons.Default.Restaurant
    "directions_car" -> Icons.Default.DirectionsCar
    "shopping_bag" -> Icons.Default.ShoppingBag
    "receipt_long" -> Icons.Default.ReceiptLong
    "home" -> Icons.Default.Home
    "theater_comedy" -> Icons.Default.TheaterComedy
    "local_hospital" -> Icons.Default.LocalHospital
    "school" -> Icons.Default.School
    "checkroom" -> Icons.Default.Checkroom
    "card_giftcard" -> Icons.Default.CardGiftcard
    "more_horiz" -> Icons.Default.MoreHoriz
    "payments" -> Icons.Default.Payments
    "work" -> Icons.Default.Work
    "trending_up" -> Icons.Default.TrendingUp
    else -> Icons.Default.Category
}
