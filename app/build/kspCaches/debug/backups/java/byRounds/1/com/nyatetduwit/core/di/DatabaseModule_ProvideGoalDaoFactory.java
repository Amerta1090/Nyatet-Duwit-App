package com.nyatetduwit.core.di;

import com.nyatetduwit.data.local.NyatetDuwitDatabase;
import com.nyatetduwit.data.local.dao.GoalDao;
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
public final class DatabaseModule_ProvideGoalDaoFactory implements Factory<GoalDao> {
  private final Provider<NyatetDuwitDatabase> databaseProvider;

  public DatabaseModule_ProvideGoalDaoFactory(Provider<NyatetDuwitDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public GoalDao get() {
    return provideGoalDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideGoalDaoFactory create(
      Provider<NyatetDuwitDatabase> databaseProvider) {
    return new DatabaseModule_ProvideGoalDaoFactory(databaseProvider);
  }

  public static GoalDao provideGoalDao(NyatetDuwitDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideGoalDao(database));
  }
}
