package com.nyatetduwit.data.local;

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
public final class PdfExportManager_Factory implements Factory<PdfExportManager> {
  private final Provider<TransactionDao> transactionDaoProvider;

  public PdfExportManager_Factory(Provider<TransactionDao> transactionDaoProvider) {
    this.transactionDaoProvider = transactionDaoProvider;
  }

  @Override
  public PdfExportManager get() {
    return newInstance(transactionDaoProvider.get());
  }

  public static PdfExportManager_Factory create(Provider<TransactionDao> transactionDaoProvider) {
    return new PdfExportManager_Factory(transactionDaoProvider);
  }

  public static PdfExportManager newInstance(TransactionDao transactionDao) {
    return new PdfExportManager(transactionDao);
  }
}
