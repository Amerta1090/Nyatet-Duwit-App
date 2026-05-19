package com.nyatetduwit.core.di

import android.content.Context
import androidx.room.Room
import com.nyatetduwit.data.local.NyatetDuwitDatabase
import com.nyatetduwit.data.local.dao.AccountDao
import com.nyatetduwit.data.local.dao.CategoryDao
import com.nyatetduwit.data.local.dao.TransactionDao
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
}
