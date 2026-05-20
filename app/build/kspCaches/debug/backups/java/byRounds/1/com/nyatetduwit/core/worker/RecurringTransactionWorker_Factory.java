package com.nyatetduwit.core.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.nyatetduwit.domain.repository.RecurringTransactionRepository;
import com.nyatetduwit.domain.repository.TransactionRepository;
import dagger.internal.DaggerGenerated;
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
public final class RecurringTransactionWorker_Factory {
  private final Provider<RecurringTransactionRepository> recurringTransactionRepositoryProvider;

  private final Provider<TransactionRepository> transactionRepositoryProvider;

  public RecurringTransactionWorker_Factory(
      Provider<RecurringTransactionRepository> recurringTransactionRepositoryProvider,
      Provider<TransactionRepository> transactionRepositoryProvider) {
    this.recurringTransactionRepositoryProvider = recurringTransactionRepositoryProvider;
    this.transactionRepositoryProvider = transactionRepositoryProvider;
  }

  public RecurringTransactionWorker get(Context context, WorkerParameters workerParams) {
    return newInstance(context, workerParams, recurringTransactionRepositoryProvider.get(), transactionRepositoryProvider.get());
  }

  public static RecurringTransactionWorker_Factory create(
      Provider<RecurringTransactionRepository> recurringTransactionRepositoryProvider,
      Provider<TransactionRepository> transactionRepositoryProvider) {
    return new RecurringTransactionWorker_Factory(recurringTransactionRepositoryProvider, transactionRepositoryProvider);
  }

  public static RecurringTransactionWorker newInstance(Context context,
      WorkerParameters workerParams, RecurringTransactionRepository recurringTransactionRepository,
      TransactionRepository transactionRepository) {
    return new RecurringTransactionWorker(context, workerParams, recurringTransactionRepository, transactionRepository);
  }
}
