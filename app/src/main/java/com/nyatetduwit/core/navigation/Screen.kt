package com.nyatetduwit.core.navigation

sealed class Screen(val route: String) {
    data object Dashboard : Screen("dashboard")
    data object Accounts : Screen("accounts")
    data object Categories : Screen("categories")
    data object AccountForm : Screen("account_form/{accountId}") {
        fun createRoute(accountId: String? = null) =
            if (accountId != null) "account_form/$accountId" else "account_form/null"
    }
    data object CategoryForm : Screen("category_form/{categoryId}") {
        fun createRoute(categoryId: String? = null) =
            if (categoryId != null) "category_form/$categoryId" else "category_form/null"
    }
}
