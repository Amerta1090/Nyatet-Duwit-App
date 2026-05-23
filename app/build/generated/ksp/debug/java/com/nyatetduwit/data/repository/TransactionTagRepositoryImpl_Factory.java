package com.nyatetduwit.data.repository;

import com.nyatetduwit.data.local.dao.TransactionTagDao;
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
public final class TransactionTagRepositoryImpl_Factory implements Factory<TransactionTagRepositoryImpl> {
  private final Provider<TransactionTagDao> tagDaoProvider;

  public TransactionTagRepositoryImpl_Factory(Provider<TransactionTagDao> tagDaoProvider) {
    this.tagDaoProvider = tagDaoProvider;
  }

  @Override
  public TransactionTagRepositoryImpl get() {
    return newInstance(tagDaoProvider.get());
  }

  public static TransactionTagRepositoryImpl_Factory create(
      Provider<TransactionTagDao> tagDaoProvider) {
    return new TransactionTagRepositoryImpl_Factory(tagDaoProvider);
  }

  public static TransactionTagRepositoryImpl newInstance(TransactionTagDao tagDao) {
    return new TransactionTagRepositoryImpl(tagDao);
  }
}
