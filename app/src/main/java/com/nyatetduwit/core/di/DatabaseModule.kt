package com.nyatetduwit.core.di

import android.content.Context
import androidx.room.Room
import com.nyatetduwit.data.local.ExportManager
import com.nyatetduwit.data.local.NyatetDuwitDatabase
import com.nyatetduwit.data.local.dao.AccountDao
import com.nyatetduwit.data.local.dao.BudgetDao
import com.nyatetduwit.data.local.dao.CategoryDao
import com.nyatetduwit.data.local.dao.DebtDao
import com.nyatetduwit.data.local.dao.ExchangeRateDao
import com.nyatetduwit.data.local.dao.GoalDao
import com.nyatetduwit.data.local.dao.InvestmentDao
import com.nyatetduwit.data.local.dao.RecurringTransactionDao
import com.nyatetduwit.data.local.dao.SplitBillDao
import com.nyatetduwit.data.local.dao.TemplateDao
import com.nyatetduwit.data.local.dao.TransactionDao
import com.nyatetduwit.data.local.dao.TransactionSplitDao
import com.nyatetduwit.data.local.dao.TransactionTagDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): NyatetDuwitDatabase {
        return Room.databaseBuilder(
            context,
            NyatetDuwitDatabase::class.java,
            "nyatetduwit_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideAccountDao(database: NyatetDuwitDatabase): AccountDao {
        return database.accountDao()
    }

    @Provides
    fun provideCategoryDao(database: NyatetDuwitDatabase): CategoryDao {
        return database.categoryDao()
    }

    @Provides
    fun provideTransactionDao(database: NyatetDuwitDatabase): TransactionDao {
        return database.transactionDao()
    }

    @Provides
    fun provideBudgetDao(database: NyatetDuwitDatabase): BudgetDao {
        return database.budgetDao()
    }

    @Provides
    fun provideRecurringTransactionDao(database: NyatetDuwitDatabase): RecurringTransactionDao {
        return database.recurringTransactionDao()
    }

    @Provides
    fun provideTemplateDao(database: NyatetDuwitDatabase): TemplateDao {
        return database.templateDao()
    }

    @Provides
    fun provideTransactionSplitDao(database: NyatetDuwitDatabase): TransactionSplitDao {
        return database.transactionSplitDao()
    }

    @Provides
    fun provideTransactionTagDao(database: NyatetDuwitDatabase): TransactionTagDao {
        return database.transactionTagDao()
    }

    @Provides
    fun provideGoalDao(database: NyatetDuwitDatabase): GoalDao {
        return database.goalDao()
    }

    @Provides
    fun provideDebtDao(database: NyatetDuwitDatabase): DebtDao {
        return database.debtDao()
    }

    @Provides
    fun provideExchangeRateDao(database: NyatetDuwitDatabase): ExchangeRateDao {
        return database.exchangeRateDao()
    }

    @Provides
    fun provideInvestmentDao(database: NyatetDuwitDatabase): InvestmentDao {
        return database.investmentDao()
    }

    @Provides
    fun provideSplitBillDao(database: NyatetDuwitDatabase): SplitBillDao {
        return database.splitBillDao()
    }

    @Provides
    @Singleton
    fun provideExportManager(
        transactionDao: TransactionDao,
        accountDao: AccountDao,
        categoryDao: CategoryDao,
        budgetDao: BudgetDao,
        recurringTransactionDao: RecurringTransactionDao,
        templateDao: TemplateDao,
        transactionSplitDao: TransactionSplitDao,
        transactionTagDao: TransactionTagDao,
    ): ExportManager {
        return ExportManager(
            transactionDao = transactionDao,
            accountDao = accountDao,
            categoryDao = categoryDao,
            budgetDao = budgetDao,
            recurringTransactionDao = recurringTransactionDao,
            templateDao = templateDao,
            transactionSplitDao = transactionSplitDao,
            transactionTagDao = transactionTagDao,
        )
    }
}
