package com.nyatetduwit;

import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import androidx.hilt.work.HiltWorkerFactory;
import com.nyatetduwit.domain.repository.SettingsRepository;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class NyatetDuwitApp_MembersInjector implements MembersInjector<NyatetDuwitApp> {
  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  private final Provider<DataStore<Preferences>> dataStoreProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  public NyatetDuwitApp_MembersInjector(Provider<HiltWorkerFactory> workerFactoryProvider,
      Provider<DataStore<Preferences>> dataStoreProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
    this.dataStoreProvider = dataStoreProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
  }

  public static MembersInjector<NyatetDuwitApp> create(
      Provider<HiltWorkerFactory> workerFactoryProvider,
      Provider<DataStore<Preferences>> dataStoreProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    return new NyatetDuwitApp_MembersInjector(workerFactoryProvider, dataStoreProvider, settingsRepositoryProvider);
  }

  @Override
  public void injectMembers(NyatetDuwitApp instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
    injectDataStore(instance, dataStoreProvider.get());
    injectSettingsRepository(instance, settingsRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.nyatetduwit.NyatetDuwitApp.workerFactory")
  public static void injectWorkerFactory(NyatetDuwitApp instance, HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }

  @InjectedFieldSignature("com.nyatetduwit.NyatetDuwitApp.dataStore")
  public static void injectDataStore(NyatetDuwitApp instance, DataStore<Preferences> dataStore) {
    instance.dataStore = dataStore;
  }

  @InjectedFieldSignature("com.nyatetduwit.NyatetDuwitApp.settingsRepository")
  public static void injectSettingsRepository(NyatetDuwitApp instance,
      SettingsRepository settingsRepository) {
    instance.settingsRepository = settingsRepository;
  }
}
