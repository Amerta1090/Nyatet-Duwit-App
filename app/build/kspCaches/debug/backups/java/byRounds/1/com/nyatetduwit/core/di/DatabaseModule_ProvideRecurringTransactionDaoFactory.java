package com.nyatetduwit.core.di;

import com.nyatetduwit.data.local.NyatetDuwitDatabase;
import com.nyatetduwit.data.local.dao.RecurringTransactionDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideRecurringTransactionDaoFactory implements Factory<RecurringTransactionDao> {
  private final Provider<NyatetDuwitDatabase> databaseProvider;

  public DatabaseModule_ProvideRecurringTransactionDaoFactory(
      Provider<NyatetDuwitDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public RecurringTransactionDao get() {
    return provideRecurringTransactionDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideRecurringTransactionDaoFactory create(
      Provider<NyatetDuwitDatabase> databaseProvider) {
    return new DatabaseModule_ProvideRecurringTransactionDaoFactory(databaseProvider);
  }

  public static RecurringTransactionDao provideRecurringTransactionDao(
      NyatetDuwitDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideRecurringTransactionDao(database));
  }
}
