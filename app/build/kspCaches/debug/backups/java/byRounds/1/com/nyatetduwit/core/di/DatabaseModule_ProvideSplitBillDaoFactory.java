package com.nyatetduwit.core.di;

import com.nyatetduwit.data.local.NyatetDuwitDatabase;
import com.nyatetduwit.data.local.dao.SplitBillDao;
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
public final class DatabaseModule_ProvideSplitBillDaoFactory implements Factory<SplitBillDao> {
  private final Provider<NyatetDuwitDatabase> databaseProvider;

  public DatabaseModule_ProvideSplitBillDaoFactory(Provider<NyatetDuwitDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public SplitBillDao get() {
    return provideSplitBillDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideSplitBillDaoFactory create(
      Provider<NyatetDuwitDatabase> databaseProvider) {
    return new DatabaseModule_ProvideSplitBillDaoFactory(databaseProvider);
  }

  public static SplitBillDao provideSplitBillDao(NyatetDuwitDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideSplitBillDao(database));
  }
}
