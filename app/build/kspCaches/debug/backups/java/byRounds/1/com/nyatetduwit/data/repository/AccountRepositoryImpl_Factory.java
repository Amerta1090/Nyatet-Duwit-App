package com.nyatetduwit.data.repository;

import com.nyatetduwit.data.local.dao.AccountDao;
import com.nyatetduwit.data.local.dao.TransactionDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class AccountRepositoryImpl_Factory implements Factory<AccountRepositoryImpl> {
  private final Provider<AccountDao> accountDaoProvider;

  private final Provider<TransactionDao> transactionDaoProvider;

  public AccountRepositoryImpl_Factory(Provider<AccountDao> accountDaoProvider,
      Provider<TransactionDao> transactionDaoProvider) {
    this.accountDaoProvider = accountDaoProvider;
    this.transactionDaoProvider = transactionDaoProvider;
  }

  @Override
  public AccountRepositoryImpl get() {
    return newInstance(accountDaoProvider.get(), transactionDaoProvider.get());
  }

  public static AccountRepositoryImpl_Factory create(Provider<AccountDao> accountDaoProvider,
      Provider<TransactionDao> transactionDaoProvider) {
    return new AccountRepositoryImpl_Factory(accountDaoProvider, transactionDaoProvider);
  }

  public static AccountRepositoryImpl newInstance(AccountDao accountDao,
      TransactionDao transactionDao) {
    return new AccountRepositoryImpl(accountDao, transactionDao);
  }
}
