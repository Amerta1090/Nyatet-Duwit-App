package com.nyatetduwit.presentation.settings;

import com.nyatetduwit.data.local.ExportManager;
import com.nyatetduwit.domain.repository.SettingsRepository;
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
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<ExportManager> exportManagerProvider;

  public SettingsViewModel_Factory(Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<ExportManager> exportManagerProvider) {
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.exportManagerProvider = exportManagerProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(settingsRepositoryProvider.get(), exportManagerProvider.get());
  }

  public static SettingsViewModel_Factory create(
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<ExportManager> exportManagerProvider) {
    return new SettingsViewModel_Factory(settingsRepositoryProvider, exportManagerProvider);
  }

  public static SettingsViewModel newInstance(SettingsRepository settingsRepository,
      ExportManager exportManager) {
    return new SettingsViewModel(settingsRepository, exportManager);
  }
}
