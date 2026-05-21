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
import com.nyatetduwit.presentation.budget.BudgetFormScreen
import com.nyatetduwit.presentation.budget.BudgetScreen
import com.nyatetduwit.presentation.category.CategoryFormScreen
import com.nyatetduwit.presentation.category.CategoryScreen
import com.nyatetduwit.presentation.dashboard.DashboardScreen
import com.nyatetduwit.presentation.recurring.RecurringTransactionFormScreen
import com.nyatetduwit.presentation.recurring.RecurringTransactionScreen
import com.nyatetduwit.presentation.monthlysummary.MonthlySummaryScreen
import com.nyatetduwit.presentation.remindersettings.ReminderSettingsScreen
import com.nyatetduwit.presentation.template.TemplateScreen
import com.nyatetduwit.presentation.transaction.TransactionDetailScreen
import com.nyatetduwit.presentation.transaction.TransactionFormScreen
import com.nyatetduwit.presentation.transaction.TransactionListScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun NyatetDuwitNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = Screen.Dashboard.route,
    isOnboardingCompleted: Boolean = true,
) {
    val actualStartDestination = if (!isOnboardingCompleted) Screen.Onboarding.route else startDestination

    NavHost(
        navController = navController,
        startDestination = actualStartDestination,
        modifier = modifier,
    ) {
        composable(Screen.Onboarding.route) {
            com.nyatetduwit.presentation.onboarding.OnboardingScreen(
                onComplete = {
                    val context = navController.context
                    val app = context.applicationContext as com.nyatetduwit.NyatetDuwitApp
                    kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
                        app.settingsRepository.setOnboardingCompleted(true)
                    }
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                },
                onSkip = {
                    val context = navController.context
                    val app = context.applicationContext as com.nyatetduwit.NyatetDuwitApp
                    kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
                        app.settingsRepository.setOnboardingCompleted(true)
                    }
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                },
            )
        }

        composable(Screen.PinSetup.route) {
            com.nyatetduwit.presentation.security.PinSetupScreen(
                onNavigateBack = { navController.popBackStack() },
                onPinSet = { pin ->
                    val context = navController.context
                    val app = context.applicationContext as com.nyatetduwit.NyatetDuwitApp
                    app.securityManager.setPin(pin)
                    navController.popBackStack()
                },
            )
        }

        composable(Screen.SecuritySettings.route) {
            com.nyatetduwit.presentation.security.SecuritySettingsScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToPinSetup = { navController.navigate(Screen.PinSetup.route) },
            )
        }

        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onNavigateToAccounts = { navController.navigate(Screen.Accounts.route) },
                onNavigateToCategories = { navController.navigate(Screen.Categories.route) },
                onNavigateToTransactions = { navController.navigate(Screen.Transactions.route) },
                onNavigateToBudgets = { navController.navigate(Screen.Budgets.route) },
                onNavigateToRecurring = { navController.navigate(Screen.Recurring.route) },
                onNavigateToTemplates = { navController.navigate(Screen.Templates.route) },
                onNavigateToMonthlySummary = { navController.navigate(Screen.MonthlySummary.createRoute()) },
                onNavigateToSecuritySettings = { navController.navigate(Screen.SecuritySettings.route) },
                onNavigateToAddTransaction = { navController.navigate(Screen.TransactionForm.createRoute()) },
                onNavigateToTransactionDetail = { transactionId ->
                    navController.navigate(Screen.TransactionDetail.createRoute(transactionId))
                },
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

        composable(Screen.Budgets.route) {
            BudgetScreen(
                onNavigateBack = { navController.popBackStack() },
                onAddBudget = { navController.navigate(Screen.BudgetForm.createRoute()) },
                onEditBudget = { budgetId ->
                    navController.navigate(Screen.BudgetForm.createRoute(budgetId))
                },
            )
        }

        composable(
            route = Screen.BudgetForm.route,
            arguments = listOf(navArgument("budgetId") { type = NavType.StringType }),
        ) { backStackEntry ->
            val budgetId = backStackEntry.arguments?.getString("budgetId")
            BudgetFormScreen(
                budgetId = if (budgetId == "null") null else budgetId,
                onNavigateBack = { navController.popBackStack() },
            )
        }

        composable(Screen.Recurring.route) {
            RecurringTransactionScreen(
                onNavigateBack = { navController.popBackStack() },
                onAddRecurring = { navController.navigate(Screen.RecurringForm.createRoute()) },
            )
        }

        composable(Screen.Templates.route) {
            TemplateScreen(
                onNavigateBack = { navController.popBackStack() },
                onTemplateClick = { templateId ->
                    navController.navigate(Screen.TransactionForm.createRoute(null))
                },
            )
        }

        composable(
            route = Screen.RecurringForm.route,
            arguments = listOf(navArgument("recurringId") { type = NavType.StringType }),
        ) { backStackEntry ->
            val recurringId = backStackEntry.arguments?.getString("recurringId")
            RecurringTransactionFormScreen(
                recurringId = if (recurringId == "null") null else recurringId,
                onNavigateBack = { navController.popBackStack() },
            )
        }

        composable(
            route = Screen.MonthlySummary.route,
            arguments = listOf(navArgument("yearMonth") {
                type = NavType.StringType
                nullable = true
            }),
        ) {
            MonthlySummaryScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToReminderSettings = { navController.navigate(Screen.ReminderSettings.route) },
            )
        }

        composable(Screen.ReminderSettings.route) {
            ReminderSettingsScreen(
                onNavigateBack = { navController.popBackStack() },
            )
        }
    }
}
