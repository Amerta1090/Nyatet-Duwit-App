package com.nyatetduwit.core.di;

import com.nyatetduwit.data.local.NyatetDuwitDatabase;
import com.nyatetduwit.data.local.dao.InvestmentDao;
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
public final class DatabaseModule_ProvideInvestmentDaoFactory implements Factory<InvestmentDao> {
  private final Provider<NyatetDuwitDatabase> databaseProvider;

  public DatabaseModule_ProvideInvestmentDaoFactory(
      Provider<NyatetDuwitDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public InvestmentDao get() {
    return provideInvestmentDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideInvestmentDaoFactory create(
      Provider<NyatetDuwitDatabase> databaseProvider) {
    return new DatabaseModule_ProvideInvestmentDaoFactory(databaseProvider);
  }

  public static InvestmentDao provideInvestmentDao(NyatetDuwitDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideInvestmentDao(database));
  }
}
