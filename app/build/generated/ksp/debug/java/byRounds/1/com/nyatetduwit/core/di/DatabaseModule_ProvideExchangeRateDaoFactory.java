package com.nyatetduwit.core.di;

import com.nyatetduwit.data.local.NyatetDuwitDatabase;
import com.nyatetduwit.data.local.dao.ExchangeRateDao;
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
public final class DatabaseModule_ProvideExchangeRateDaoFactory implements Factory<ExchangeRateDao> {
  private final Provider<NyatetDuwitDatabase> databaseProvider;

  public DatabaseModule_ProvideExchangeRateDaoFactory(
      Provider<NyatetDuwitDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public ExchangeRateDao get() {
    return provideExchangeRateDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideExchangeRateDaoFactory create(
      Provider<NyatetDuwitDatabase> databaseProvider) {
    return new DatabaseModule_ProvideExchangeRateDaoFactory(databaseProvider);
  }

  public static ExchangeRateDao provideExchangeRateDao(NyatetDuwitDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideExchangeRateDao(database));
  }
}
