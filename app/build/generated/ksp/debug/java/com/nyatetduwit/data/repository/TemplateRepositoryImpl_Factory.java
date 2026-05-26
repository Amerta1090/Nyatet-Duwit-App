package com.nyatetduwit.data.repository;

import com.nyatetduwit.data.local.dao.TemplateDao;
import com.nyatetduwit.data.local.dao.TransactionDao;
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
public final class TemplateRepositoryImpl_Factory implements Factory<TemplateRepositoryImpl> {
  private final Provider<TemplateDao> templateDaoProvider;

  private final Provider<TransactionDao> transactionDaoProvider;

  public TemplateRepositoryImpl_Factory(Provider<TemplateDao> templateDaoProvider,
      Provider<TransactionDao> transactionDaoProvider) {
    this.templateDaoProvider = templateDaoProvider;
    this.transactionDaoProvider = transactionDaoProvider;
  }

  @Override
  public TemplateRepositoryImpl get() {
    return newInstance(templateDaoProvider.get(), transactionDaoProvider.get());
  }

  public static TemplateRepositoryImpl_Factory create(Provider<TemplateDao> templateDaoProvider,
      Provider<TransactionDao> transactionDaoProvider) {
    return new TemplateRepositoryImpl_Factory(templateDaoProvider, transactionDaoProvider);
  }

  public static TemplateRepositoryImpl newInstance(TemplateDao templateDao,
      TransactionDao transactionDao) {
    return new TemplateRepositoryImpl(templateDao, transactionDao);
  }
}
