package com.nyatetduwit.core.sync;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class SyncManager_Factory implements Factory<SyncManager> {
  private final Provider<SyncProvider> syncProvider;

  public SyncManager_Factory(Provider<SyncProvider> syncProvider) {
    this.syncProvider = syncProvider;
  }

  @Override
  public SyncManager get() {
    return newInstance(syncProvider.get());
  }

  public static SyncManager_Factory create(Provider<SyncProvider> syncProvider) {
    return new SyncManager_Factory(syncProvider);
  }

  public static SyncManager newInstance(SyncProvider syncProvider) {
    return new SyncManager(syncProvider);
  }
}
