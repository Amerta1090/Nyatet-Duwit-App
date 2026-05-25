package com.nyatetduwit.core.di

import com.nyatetduwit.data.repository.AccountRepositoryImpl
import com.nyatetduwit.data.repository.BudgetRepositoryImpl
import com.nyatetduwit.data.repository.CategoryRepositoryImpl
import com.nyatetduwit.data.repository.DebtRepositoryImpl
import com.nyatetduwit.data.repository.GoalRepositoryImpl
import com.nyatetduwit.data.repository.RecurringTransactionRepositoryImpl
import com.nyatetduwit.data.repository.SettingsRepositoryImpl
import com.nyatetduwit.data.repository.TemplateRepositoryImpl
import com.nyatetduwit.data.repository.TransactionRepositoryImpl
import com.nyatetduwit.data.repository.TransactionSplitRepositoryImpl
import com.nyatetduwit.data.repository.TransactionTagRepositoryImpl
import com.nyatetduwit.domain.repository.AccountRepository
import com.nyatetduwit.domain.repository.BudgetRepository
import com.nyatetduwit.domain.repository.CategoryRepository
import com.nyatetduwit.domain.repository.DebtRepository
import com.nyatetduwit.domain.repository.GoalRepository
import com.nyatetduwit.domain.repository.RecurringTransactionRepository
import com.nyatetduwit.domain.repository.SettingsRepository
import com.nyatetduwit.domain.repository.TemplateRepository
import com.nyatetduwit.domain.repository.TransactionRepository
import com.nyatetduwit.domain.repository.TransactionSplitRepository
import com.nyatetduwit.domain.repository.TransactionTagRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAccountRepository(impl: AccountRepositoryImpl): AccountRepository

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(impl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    @Singleton
    abstract fun bindTransactionRepository(impl: TransactionRepositoryImpl): TransactionRepository

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository

    @Binds
    @Singleton
    abstract fun bindBudgetRepository(impl: BudgetRepositoryImpl): BudgetRepository

    @Binds
    @Singleton
    abstract fun bindRecurringTransactionRepository(impl: RecurringTransactionRepositoryImpl): RecurringTransactionRepository

    @Binds
    @Singleton
    abstract fun bindTemplateRepository(impl: TemplateRepositoryImpl): TemplateRepository

    @Binds
    @Singleton
    abstract fun bindTransactionSplitRepository(impl: TransactionSplitRepositoryImpl): TransactionSplitRepository

    @Binds
    @Singleton
    abstract fun bindTransactionTagRepository(impl: TransactionTagRepositoryImpl): TransactionTagRepository

    @Binds
    @Singleton
    abstract fun bindGoalRepository(impl: GoalRepositoryImpl): GoalRepository

    @Binds
    @Singleton
    abstract fun bindDebtRepository(impl: DebtRepositoryImpl): DebtRepository
}
