package com.nyatetduwit.core.worker;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class ReminderScheduler_Factory implements Factory<ReminderScheduler> {
  @Override
  public ReminderScheduler get() {
    return newInstance();
  }

  public static ReminderScheduler_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static ReminderScheduler newInstance() {
    return new ReminderScheduler();
  }

  private static final class InstanceHolder {
    private static final ReminderScheduler_Factory INSTANCE = new ReminderScheduler_Factory();
  }
}
