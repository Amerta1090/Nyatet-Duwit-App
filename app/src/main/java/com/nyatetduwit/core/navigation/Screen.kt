package com.nyatetduwit.core.navigation

sealed class Screen(val route: String) {
    data object Dashboard : Screen("dashboard")
    data object Accounts : Screen("accounts")
    data object Categories : Screen("categories")
    data object Transactions : Screen("transactions")
    data object Budgets : Screen("budgets")
    data object Recurring : Screen("recurring")
    data object AccountForm : Screen("account_form/{accountId}") {
        fun createRoute(accountId: String? = null) =
            if (accountId != null) "account_form/$accountId" else "account_form/null"
    }
    data object CategoryForm : Screen("category_form/{categoryId}") {
        fun createRoute(categoryId: String? = null) =
            if (categoryId != null) "category_form/$categoryId" else "category_form/null"
    }
    data object TransactionForm : Screen("transaction_form/{transactionId}") {
        fun createRoute(transactionId: String? = null) =
            if (transactionId != null) "transaction_form/$transactionId" else "transaction_form/null"
    }
    data object TransactionDetail : Screen("transaction_detail/{transactionId}") {
        fun createRoute(transactionId: String) = "transaction_detail/$transactionId"
    }
    data object BudgetForm : Screen("budget_form/{budgetId}") {
        fun createRoute(budgetId: String? = null) =
            if (budgetId != null) "budget_form/$budgetId" else "budget_form/null"
    }
    data object RecurringForm : Screen("recurring_form/{recurringId}") {
        fun createRoute(recurringId: String? = null) =
            if (recurringId != null) "recurring_form/$recurringId" else "recurring_form/null"
    }
}
