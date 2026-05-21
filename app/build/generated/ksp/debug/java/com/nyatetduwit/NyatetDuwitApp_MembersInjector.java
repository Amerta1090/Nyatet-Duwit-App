package com.nyatetduwit;

import androidx.hilt.work.HiltWorkerFactory;
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

  public NyatetDuwitApp_MembersInjector(Provider<HiltWorkerFactory> workerFactoryProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
  }

  public static MembersInjector<NyatetDuwitApp> create(
      Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new NyatetDuwitApp_MembersInjector(workerFactoryProvider);
  }

  @Override
  public void injectMembers(NyatetDuwitApp instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
  }

  @InjectedFieldSignature("com.nyatetduwit.NyatetDuwitApp.workerFactory")
  public static void injectWorkerFactory(NyatetDuwitApp instance, HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }
}
