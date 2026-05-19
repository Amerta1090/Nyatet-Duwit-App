package com.nyatetduwit.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nyatetduwit.data.local.dao.AccountDao
import com.nyatetduwit.data.local.dao.CategoryDao
import com.nyatetduwit.data.local.entity.AccountEntity
import com.nyatetduwit.data.local.entity.CategoryEntity

@Database(
    entities = [
        AccountEntity::class,
        CategoryEntity::class,
    ],
    version = 1,
    exportSchema = true
)
abstract class NyatetDuwitDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun categoryDao(): CategoryDao
}
