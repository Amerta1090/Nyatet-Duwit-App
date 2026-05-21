package com.nyatetduwit.presentation.recurring;

import com.nyatetduwit.domain.repository.RecurringTransactionRepository;
import com.nyatetduwit.domain.repository.TransactionRepository;
import com.nyatetduwit.domain.usecase.recurring.AddRecurringTransactionUseCase;
import com.nyatetduwit.domain.usecase.recurring.DeactivateRecurringTransactionUseCase;
import com.nyatetduwit.domain.usecase.recurring.DeleteRecurringTransactionUseCase;
import com.nyatetduwit.domain.usecase.recurring.GetAllRecurringTransactionsUseCase;
import com.nyatetduwit.domain.usecase.recurring.GetRecurringTransactionsUseCase;
import com.nyatetduwit.domain.usecase.recurring.SkipRecurringInstanceUseCase;
import com.nyatetduwit.domain.usecase.recurring.UpdateRecurringTransactionUseCase;
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
public final class RecurringTransactionViewModel_Factory implements Factory<RecurringTransactionViewModel> {
  private final Provider<GetRecurringTransactionsUseCase> getRecurringTransactionsUseCaseProvider;

  private final Provider<GetAllRecurringTransactionsUseCase> getAllRecurringTransactionsUseCaseProvider;

  private final Provider<RecurringTransactionRepository> recurringTransactionRepositoryProvider;

  private final Provider<TransactionRepository> transactionRepositoryProvider;

  private final Provider<AddRecurringTransactionUseCase> addRecurringTransactionUseCaseProvider;

  private final Provider<UpdateRecurringTransactionUseCase> updateRecurringTransactionUseCaseProvider;

  private final Provider<DeleteRecurringTransactionUseCase> deleteRecurringTransactionUseCaseProvider;

  private final Provider<DeactivateRecurringTransactionUseCase> deactivateRecurringTransactionUseCaseProvider;

  private final Provider<SkipRecurringInstanceUseCase> skipRecurringInstanceUseCaseProvider;

  public RecurringTransactionViewModel_Factory(
      Provider<GetRecurringTransactionsUseCase> getRecurringTransactionsUseCaseProvider,
      Provider<GetAllRecurringTransactionsUseCase> getAllRecurringTransactionsUseCaseProvider,
      Provider<RecurringTransactionRepository> recurringTransactionRepositoryProvider,
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<AddRecurringTransactionUseCase> addRecurringTransactionUseCaseProvider,
      Provider<UpdateRecurringTransactionUseCase> updateRecurringTransactionUseCaseProvider,
      Provider<DeleteRecurringTransactionUseCase> deleteRecurringTransactionUseCaseProvider,
      Provider<DeactivateRecurringTransactionUseCase> deactivateRecurringTransactionUseCaseProvider,
      Provider<SkipRecurringInstanceUseCase> skipRecurringInstanceUseCaseProvider) {
    this.getRecurringTransactionsUseCaseProvider = getRecurringTransactionsUseCaseProvider;
    this.getAllRecurringTransactionsUseCaseProvider = getAllRecurringTransactionsUseCaseProvider;
    this.recurringTransactionRepositoryProvider = recurringTransactionRepositoryProvider;
    this.transactionRepositoryProvider = transactionRepositoryProvider;
    this.addRecurringTransactionUseCaseProvider = addRecurringTransactionUseCaseProvider;
    this.updateRecurringTransactionUseCaseProvider = updateRecurringTransactionUseCaseProvider;
    this.deleteRecurringTransactionUseCaseProvider = deleteRecurringTransactionUseCaseProvider;
    this.deactivateRecurringTransactionUseCaseProvider = deactivateRecurringTransactionUseCaseProvider;
    this.skipRecurringInstanceUseCaseProvider = skipRecurringInstanceUseCaseProvider;
  }

  @Override
  public RecurringTransactionViewModel get() {
    return newInstance(getRecurringTransactionsUseCaseProvider.get(), getAllRecurringTransactionsUseCaseProvider.get(), recurringTransactionRepositoryProvider.get(), transactionRepositoryProvider.get(), addRecurringTransactionUseCaseProvider.get(), updateRecurringTransactionUseCaseProvider.get(), deleteRecurringTransactionUseCaseProvider.get(), deactivateRecurringTransactionUseCaseProvider.get(), skipRecurringInstanceUseCaseProvider.get());
  }

  public static RecurringTransactionViewModel_Factory create(
      Provider<GetRecurringTransactionsUseCase> getRecurringTransactionsUseCaseProvider,
      Provider<GetAllRecurringTransactionsUseCase> getAllRecurringTransactionsUseCaseProvider,
      Provider<RecurringTransactionRepository> recurringTransactionRepositoryProvider,
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<AddRecurringTransactionUseCase> addRecurringTransactionUseCaseProvider,
      Provider<UpdateRecurringTransactionUseCase> updateRecurringTransactionUseCaseProvider,
      Provider<DeleteRecurringTransactionUseCase> deleteRecurringTransactionUseCaseProvider,
      Provider<DeactivateRecurringTransactionUseCase> deactivateRecurringTransactionUseCaseProvider,
      Provider<SkipRecurringInstanceUseCase> skipRecurringInstanceUseCaseProvider) {
    return new RecurringTransactionViewModel_Factory(getRecurringTransactionsUseCaseProvider, getAllRecurringTransactionsUseCaseProvider, recurringTransactionRepositoryProvider, transactionRepositoryProvider, addRecurringTransactionUseCaseProvider, updateRecurringTransactionUseCaseProvider, deleteRecurringTransactionUseCaseProvider, deactivateRecurringTransactionUseCaseProvider, skipRecurringInstanceUseCaseProvider);
  }

  public static RecurringTransactionViewModel newInstance(
      GetRecurringTransactionsUseCase getRecurringTransactionsUseCase,
      GetAllRecurringTransactionsUseCase getAllRecurringTransactionsUseCase,
      RecurringTransactionRepository recurringTransactionRepository,
      TransactionRepository transactionRepository,
      AddRecurringTransactionUseCase addRecurringTransactionUseCase,
      UpdateRecurringTransactionUseCase updateRecurringTransactionUseCase,
      DeleteRecurringTransactionUseCase deleteRecurringTransactionUseCase,
      DeactivateRecurringTransactionUseCase deactivateRecurringTransactionUseCase,
      SkipRecurringInstanceUseCase skipRecurringInstanceUseCase) {
    return new RecurringTransactionViewModel(getRecurringTransactionsUseCase, getAllRecurringTransactionsUseCase, recurringTransactionRepository, transactionRepository, addRecurringTransactionUseCase, updateRecurringTransactionUseCase, deleteRecurringTransactionUseCase, deactivateRecurringTransactionUseCase, skipRecurringInstanceUseCase);
  }
}
