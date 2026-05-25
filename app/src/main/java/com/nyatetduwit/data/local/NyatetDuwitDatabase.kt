package com.nyatetduwit.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
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
import com.nyatetduwit.data.local.entity.AccountEntity
import com.nyatetduwit.data.local.entity.BudgetEntity
import com.nyatetduwit.data.local.entity.CategoryEntity
import com.nyatetduwit.data.local.entity.DebtEntity
import com.nyatetduwit.data.local.entity.DebtPaymentEntity
import com.nyatetduwit.data.local.entity.ExchangeRateEntity
import com.nyatetduwit.data.local.entity.GoalEntity
import com.nyatetduwit.data.local.entity.InvestmentEntity
import com.nyatetduwit.data.local.entity.RecurringTransactionEntity
import com.nyatetduwit.data.local.entity.SplitBillEntity
import com.nyatetduwit.data.local.entity.SplitBillPersonEntity
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
        GoalEntity::class,
        DebtEntity::class,
        DebtPaymentEntity::class,
        ExchangeRateEntity::class,
        InvestmentEntity::class,
        SplitBillEntity::class,
        SplitBillPersonEntity::class,
    ],
    version = 7,
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
    abstract fun goalDao(): GoalDao
    abstract fun debtDao(): DebtDao
    abstract fun exchangeRateDao(): ExchangeRateDao
    abstract fun investmentDao(): InvestmentDao
    abstract fun splitBillDao(): SplitBillDao

    companion object {
        @Volatile
        private var INSTANCE: NyatetDuwitDatabase? = null

        fun getInstance(context: Context): NyatetDuwitDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    NyatetDuwitDatabase::class.java,
                    "nyatetduwit_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
