package com.nyatetduwit.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nyatetduwit.data.local.dao.AccountDao
import com.nyatetduwit.data.local.dao.BudgetDao
import com.nyatetduwit.data.local.dao.CategoryDao
import com.nyatetduwit.data.local.dao.RecurringTransactionDao
import com.nyatetduwit.data.local.dao.TemplateDao
import com.nyatetduwit.data.local.dao.TransactionDao
import com.nyatetduwit.data.local.dao.TransactionSplitDao
import com.nyatetduwit.data.local.dao.TransactionTagDao
import com.nyatetduwit.data.local.entity.AccountEntity
import com.nyatetduwit.data.local.entity.BudgetEntity
import com.nyatetduwit.data.local.entity.CategoryEntity
import com.nyatetduwit.data.local.entity.RecurringTransactionEntity
import com.nyatetduwit.data.local.entity.TemplateEntity
import com.nyatetduwit.data.local.entity.TransactionEntity
import com.nyatetduwit.data.local.entity.TransactionSplitEntity
import com.nyatetduwit.data.local.entity.TransactionTagEntity

@Database(
    entities = [
        AccountEntity::class,
        CategoryEntity::class,
        TransactionEntity::class,
        BudgetEntity::class,
        RecurringTransactionEntity::class,
        TemplateEntity::class,
        TransactionSplitEntity::class,
        TransactionTagEntity::class,
    ],
    version = 5,
    exportSchema = true
)
abstract class NyatetDuwitDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun categoryDao(): CategoryDao
    abstract fun transactionDao(): TransactionDao
    abstract fun budgetDao(): BudgetDao
    abstract fun recurringTransactionDao(): RecurringTransactionDao
    abstract fun templateDao(): TemplateDao
    abstract fun transactionSplitDao(): TransactionSplitDao
    abstract fun transactionTagDao(): TransactionTagDao
}
