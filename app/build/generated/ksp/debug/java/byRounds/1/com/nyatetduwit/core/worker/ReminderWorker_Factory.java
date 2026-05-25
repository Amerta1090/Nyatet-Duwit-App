package com.nyatetduwit.core.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.nyatetduwit.domain.repository.SettingsRepository;
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
public final class ReminderWorker_Factory {
  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<TransactionRepository> transactionRepositoryProvider;

  public ReminderWorker_Factory(Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<TransactionRepository> transactionRepositoryProvider) {
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.transactionRepositoryProvider = transactionRepositoryProvider;
  }

  public ReminderWorker get(Context context, WorkerParameters workerParams) {
    return newInstance(context, workerParams, settingsRepositoryProvider.get(), transactionRepositoryProvider.get());
  }

  public static ReminderWorker_Factory create(
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<TransactionRepository> transactionRepositoryProvider) {
    return new ReminderWorker_Factory(settingsRepositoryProvider, transactionRepositoryProvider);
  }

  public static ReminderWorker newInstance(Context context, WorkerParameters workerParams,
      SettingsRepository settingsRepository, TransactionRepository transactionRepository) {
    return new ReminderWorker(context, workerParams, settingsRepository, transactionRepository);
  }
}
