package com.nyatetduwit.domain.usecase.transaction;

import com.nyatetduwit.domain.repository.AccountRepository;
import com.nyatetduwit.domain.repository.TransactionRepository;
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
public final class BalanceUpdateService_Factory implements Factory<BalanceUpdateService> {
  private final Provider<AccountRepository> accountRepositoryProvider;

  private final Provider<TransactionRepository> transactionRepositoryProvider;

  public BalanceUpdateService_Factory(Provider<AccountRepository> accountRepositoryProvider,
      Provider<TransactionRepository> transactionRepositoryProvider) {
    this.accountRepositoryProvider = accountRepositoryProvider;
    this.transactionRepositoryProvider = transactionRepositoryProvider;
  }

  @Override
  public BalanceUpdateService get() {
    return newInstance(accountRepositoryProvider.get(), transactionRepositoryProvider.get());
  }

  public static BalanceUpdateService_Factory create(
      Provider<AccountRepository> accountRepositoryProvider,
      Provider<TransactionRepository> transactionRepositoryProvider) {
    return new BalanceUpdateService_Factory(accountRepositoryProvider, transactionRepositoryProvider);
  }

  public static BalanceUpdateService newInstance(AccountRepository accountRepository,
      TransactionRepository transactionRepository) {
    return new BalanceUpdateService(accountRepository, transactionRepository);
  }
}
