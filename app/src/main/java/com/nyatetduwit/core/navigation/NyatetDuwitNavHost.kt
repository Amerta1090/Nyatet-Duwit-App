package com.nyatetduwit.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.nyatetduwit.presentation.account.AccountFormScreen
import com.nyatetduwit.presentation.account.AccountScreen
import com.nyatetduwit.presentation.category.CategoryFormScreen
import com.nyatetduwit.presentation.category.CategoryScreen
import com.nyatetduwit.presentation.dashboard.DashboardScreen
import com.nyatetduwit.presentation.transaction.TransactionDetailScreen
import com.nyatetduwit.presentation.transaction.TransactionFormScreen
import com.nyatetduwit.presentation.transaction.TransactionListScreen

@Composable
fun NyatetDuwitNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = Screen.Dashboard.route,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onNavigateToAccounts = { navController.navigate(Screen.Accounts.route) },
                onNavigateToCategories = { navController.navigate(Screen.Categories.route) },
                onNavigateToTransactions = { navController.navigate(Screen.Transactions.route) },
                onNavigateToAddTransaction = { navController.navigate(Screen.TransactionForm.createRoute()) },
            )
        }

        composable(Screen.Accounts.route) {
            AccountScreen(
                onNavigateBack = { navController.popBackStack() },
                onAddAccount = { navController.navigate(Screen.AccountForm.createRoute()) },
                onEditAccount = { accountId ->
                    navController.navigate(Screen.AccountForm.createRoute(accountId))
                },
            )
        }

        composable(
            route = Screen.AccountForm.route,
            arguments = listOf(navArgument("accountId") { type = NavType.StringType }),
        ) { backStackEntry ->
            val accountId = backStackEntry.arguments?.getString("accountId")
            AccountFormScreen(
                accountId = if (accountId == "null") null else accountId,
                onNavigateBack = { navController.popBackStack() },
            )
        }

        composable(Screen.Categories.route) {
            CategoryScreen(
                onNavigateBack = { navController.popBackStack() },
                onAddCategory = { navController.navigate(Screen.CategoryForm.createRoute()) },
                onEditCategory = { categoryId ->
                    navController.navigate(Screen.CategoryForm.createRoute(categoryId))
                },
            )
        }

        composable(
            route = Screen.CategoryForm.route,
            arguments = listOf(navArgument("categoryId") { type = NavType.StringType }),
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId")
            CategoryFormScreen(
                categoryId = if (categoryId == "null") null else categoryId,
                onNavigateBack = { navController.popBackStack() },
            )
        }

        composable(Screen.Transactions.route) {
            TransactionListScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToAddTransaction = { navController.navigate(Screen.TransactionForm.createRoute()) },
                onTransactionClick = { transactionId ->
                    navController.navigate(Screen.TransactionDetail.createRoute(transactionId))
                },
                onTransactionEdit = { transactionId ->
                    navController.navigate(Screen.TransactionForm.createRoute(transactionId))
                },
            )
        }

        composable(
            route = Screen.TransactionForm.route,
            arguments = listOf(navArgument("transactionId") {
                type = NavType.StringType
                nullable = true
            }),
        ) { backStackEntry ->
            val transactionId = backStackEntry.arguments?.getString("transactionId")
            TransactionFormScreen(
                transactionId = if (transactionId == "null") null else transactionId,
                onNavigateBack = { navController.popBackStack() },
            )
        }

        composable(
            route = Screen.TransactionDetail.route,
            arguments = listOf(navArgument("transactionId") { type = NavType.StringType }),
        ) { backStackEntry ->
            val transactionId = backStackEntry.arguments?.getString("transactionId") ?: return@composable
            TransactionDetailScreen(
                transactionId = transactionId,
                onNavigateBack = { navController.popBackStack() },
                onEditTransaction = { id ->
                    navController.navigate(Screen.TransactionForm.createRoute(id))
                },
            )
        }
    }
}
