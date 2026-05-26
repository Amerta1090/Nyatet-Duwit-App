package com.nyatetduwit.data.repository;

import com.nyatetduwit.data.local.dao.SplitBillDao;
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
public final class SplitBillRepositoryImpl_Factory implements Factory<SplitBillRepositoryImpl> {
  private final Provider<SplitBillDao> splitBillDaoProvider;

  public SplitBillRepositoryImpl_Factory(Provider<SplitBillDao> splitBillDaoProvider) {
    this.splitBillDaoProvider = splitBillDaoProvider;
  }

  @Override
  public SplitBillRepositoryImpl get() {
    return newInstance(splitBillDaoProvider.get());
  }

  public static SplitBillRepositoryImpl_Factory create(
      Provider<SplitBillDao> splitBillDaoProvider) {
    return new SplitBillRepositoryImpl_Factory(splitBillDaoProvider);
  }

  public static SplitBillRepositoryImpl newInstance(SplitBillDao splitBillDao) {
    return new SplitBillRepositoryImpl(splitBillDao);
  }
}
