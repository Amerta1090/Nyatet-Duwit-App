package com.nyatetduwit.core.sync;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class LocalSyncProvider_Factory implements Factory<LocalSyncProvider> {
  @Override
  public LocalSyncProvider get() {
    return newInstance();
  }

  public static LocalSyncProvider_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static LocalSyncProvider newInstance() {
    return new LocalSyncProvider();
  }

  private static final class InstanceHolder {
    private static final LocalSyncProvider_Factory INSTANCE = new LocalSyncProvider_Factory();
  }
}
