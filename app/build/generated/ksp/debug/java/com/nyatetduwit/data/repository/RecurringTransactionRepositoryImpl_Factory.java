package com.nyatetduwit.data.repository;

import com.nyatetduwit.data.local.dao.RecurringTransactionDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class RecurringTransactionRepositoryImpl_Factory implements Factory<RecurringTransactionRepositoryImpl> {
  private final Provider<RecurringTransactionDao> recurringDaoProvider;

  public RecurringTransactionRepositoryImpl_Factory(
      Provider<RecurringTransactionDao> recurringDaoProvider) {
    this.recurringDaoProvider = recurringDaoProvider;
  }

  @Override
  public RecurringTransactionRepositoryImpl get() {
    return newInstance(recurringDaoProvider.get());
  }

  public static RecurringTransactionRepositoryImpl_Factory create(
      Provider<RecurringTransactionDao> recurringDaoProvider) {
    return new RecurringTransactionRepositoryImpl_Factory(recurringDaoProvider);
  }

  public static RecurringTransactionRepositoryImpl newInstance(
      RecurringTransactionDao recurringDao) {
    return new RecurringTransactionRepositoryImpl(recurringDao);
  }
}
