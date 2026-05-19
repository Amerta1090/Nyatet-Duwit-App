package com.nyatetduwit.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nyatetduwit.data.local.dao.AccountDao
import com.nyatetduwit.data.local.dao.CategoryDao
import com.nyatetduwit.data.local.dao.TransactionDao
import com.nyatetduwit.data.local.entity.AccountEntity
import com.nyatetduwit.data.local.entity.CategoryEntity
import com.nyatetduwit.data.local.entity.TransactionEntity

@Database(
    entities = [
        AccountEntity::class,
        CategoryEntity::class,
        TransactionEntity::class,
    ],
    version = 2,
    exportSchema = true
)
abstract class NyatetDuwitDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun categoryDao(): CategoryDao
    abstract fun transactionDao(): TransactionDao
}
