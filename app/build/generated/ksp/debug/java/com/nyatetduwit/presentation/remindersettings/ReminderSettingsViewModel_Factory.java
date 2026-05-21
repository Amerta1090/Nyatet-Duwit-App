package com.nyatetduwit.presentation.remindersettings;

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
public final class ReminderSettingsViewModel_Factory implements Factory<ReminderSettingsViewModel> {
  private final Provider<SettingsRepository> settingsRepositoryProvider;

  public ReminderSettingsViewModel_Factory(
      Provider<SettingsRepository> settingsRepositoryProvider) {
    this.settingsRepositoryProvider = settingsRepositoryProvider;
  }

  @Override
  public ReminderSettingsViewModel get() {
    return newInstance(settingsRepositoryProvider.get());
  }

  public static ReminderSettingsViewModel_Factory create(
      Provider<SettingsRepository> settingsRepositoryProvider) {
    return new ReminderSettingsViewModel_Factory(settingsRepositoryProvider);
  }

  public static ReminderSettingsViewModel newInstance(SettingsRepository settingsRepository) {
    return new ReminderSettingsViewModel(settingsRepository);
  }
}
