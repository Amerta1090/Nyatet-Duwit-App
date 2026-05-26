package com.nyatetduwit.presentation.settings;

import android.content.Context;
import com.nyatetduwit.core.sync.SyncManager;
import com.nyatetduwit.core.worker.ReminderScheduler;
import com.nyatetduwit.data.local.ExportManager;
import com.nyatetduwit.data.local.PdfExportManager;
import com.nyatetduwit.domain.repository.SettingsRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<Context> contextProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<ExportManager> exportManagerProvider;

  private final Provider<PdfExportManager> pdfExportManagerProvider;

  private final Provider<ReminderScheduler> reminderSchedulerProvider;

  private final Provider<SyncManager> syncManagerProvider;

  public SettingsViewModel_Factory(Provider<Context> contextProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<ExportManager> exportManagerProvider,
      Provider<PdfExportManager> pdfExportManagerProvider,
      Provider<ReminderScheduler> reminderSchedulerProvider,
      Provider<SyncManager> syncManagerProvider) {
    this.contextProvider = contextProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.exportManagerProvider = exportManagerProvider;
    this.pdfExportManagerProvider = pdfExportManagerProvider;
    this.reminderSchedulerProvider = reminderSchedulerProvider;
    this.syncManagerProvider = syncManagerProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(contextProvider.get(), settingsRepositoryProvider.get(), exportManagerProvider.get(), pdfExportManagerProvider.get(), reminderSchedulerProvider.get(), syncManagerProvider.get());
  }

  public static SettingsViewModel_Factory create(Provider<Context> contextProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<ExportManager> exportManagerProvider,
      Provider<PdfExportManager> pdfExportManagerProvider,
      Provider<ReminderScheduler> reminderSchedulerProvider,
      Provider<SyncManager> syncManagerProvider) {
    return new SettingsViewModel_Factory(contextProvider, settingsRepositoryProvider, exportManagerProvider, pdfExportManagerProvider, reminderSchedulerProvider, syncManagerProvider);
  }

  public static SettingsViewModel newInstance(Context context,
      SettingsRepository settingsRepository, ExportManager exportManager,
      PdfExportManager pdfExportManager, ReminderScheduler reminderScheduler,
      SyncManager syncManager) {
    return new SettingsViewModel(context, settingsRepository, exportManager, pdfExportManager, reminderScheduler, syncManager);
  }
}
