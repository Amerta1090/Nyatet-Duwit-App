package com.nyatetduwit.data.repository;

import com.nyatetduwit.data.local.dao.TransactionSplitDao;
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
public final class TransactionSplitRepositoryImpl_Factory implements Factory<TransactionSplitRepositoryImpl> {
  private final Provider<TransactionSplitDao> splitDaoProvider;

  public TransactionSplitRepositoryImpl_Factory(Provider<TransactionSplitDao> splitDaoProvider) {
    this.splitDaoProvider = splitDaoProvider;
  }

  @Override
  public TransactionSplitRepositoryImpl get() {
    return newInstance(splitDaoProvider.get());
  }

  public static TransactionSplitRepositoryImpl_Factory create(
      Provider<TransactionSplitDao> splitDaoProvider) {
    return new TransactionSplitRepositoryImpl_Factory(splitDaoProvider);
  }

  public static TransactionSplitRepositoryImpl newInstance(TransactionSplitDao splitDao) {
    return new TransactionSplitRepositoryImpl(splitDao);
  }
}
