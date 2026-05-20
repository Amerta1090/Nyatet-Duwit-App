package com.nyatetduwit.presentation.transaction;

import com.nyatetduwit.domain.usecase.transaction.GetTransactionsUseCase;
import com.nyatetduwit.domain.usecase.transaction.RestoreTransactionUseCase;
import com.nyatetduwit.domain.usecase.transaction.SoftDeleteTransactionUseCase;
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
public final class TransactionListViewModel_Factory implements Factory<TransactionListViewModel> {
  private final Provider<GetTransactionsUseCase> getTransactionsUseCaseProvider;

  private final Provider<SoftDeleteTransactionUseCase> softDeleteTransactionUseCaseProvider;

  private final Provider<RestoreTransactionUseCase> restoreTransactionUseCaseProvider;

  public TransactionListViewModel_Factory(
      Provider<GetTransactionsUseCase> getTransactionsUseCaseProvider,
      Provider<SoftDeleteTransactionUseCase> softDeleteTransactionUseCaseProvider,
      Provider<RestoreTransactionUseCase> restoreTransactionUseCaseProvider) {
    this.getTransactionsUseCaseProvider = getTransactionsUseCaseProvider;
    this.softDeleteTransactionUseCaseProvider = softDeleteTransactionUseCaseProvider;
    this.restoreTransactionUseCaseProvider = restoreTransactionUseCaseProvider;
  }

  @Override
  public TransactionListViewModel get() {
    return newInstance(getTransactionsUseCaseProvider.get(), softDeleteTransactionUseCaseProvider.get(), restoreTransactionUseCaseProvider.get());
  }

  public static TransactionListViewModel_Factory create(
      Provider<GetTransactionsUseCase> getTransactionsUseCaseProvider,
      Provider<SoftDeleteTransactionUseCase> softDeleteTransactionUseCaseProvider,
      Provider<RestoreTransactionUseCase> restoreTransactionUseCaseProvider) {
    return new TransactionListViewModel_Factory(getTransactionsUseCaseProvider, softDeleteTransactionUseCaseProvider, restoreTransactionUseCaseProvider);
  }

  public static TransactionListViewModel newInstance(GetTransactionsUseCase getTransactionsUseCase,
      SoftDeleteTransactionUseCase softDeleteTransactionUseCase,
      RestoreTransactionUseCase restoreTransactionUseCase) {
    return new TransactionListViewModel(getTransactionsUseCase, softDeleteTransactionUseCase, restoreTransactionUseCase);
  }
}
