package com.nyatetduwit.core.di

import com.nyatetduwit.data.repository.AccountRepositoryImpl
import com.nyatetduwit.data.repository.CategoryRepositoryImpl
import com.nyatetduwit.data.repository.TransactionRepositoryImpl
import com.nyatetduwit.domain.repository.AccountRepository
import com.nyatetduwit.domain.repository.CategoryRepository
import com.nyatetduwit.domain.repository.TransactionRepository
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
}
