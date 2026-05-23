package com.nyatetduwit.core.di;

import com.nyatetduwit.data.local.ExportManager;
import com.nyatetduwit.data.local.dao.AccountDao;
import com.nyatetduwit.data.local.dao.BudgetDao;
import com.nyatetduwit.data.local.dao.CategoryDao;
import com.nyatetduwit.data.local.dao.RecurringTransactionDao;
import com.nyatetduwit.data.local.dao.TemplateDao;
import com.nyatetduwit.data.local.dao.TransactionDao;
import com.nyatetduwit.data.local.dao.TransactionSplitDao;
import com.nyatetduwit.data.local.dao.TransactionTagDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class DatabaseModule_ProvideExportManagerFactory implements Factory<ExportManager> {
  private final Provider<TransactionDao> transactionDaoProvider;

  private final Provider<AccountDao> accountDaoProvider;

  private final Provider<CategoryDao> categoryDaoProvider;

  private final Provider<BudgetDao> budgetDaoProvider;

  private final Provider<RecurringTransactionDao> recurringTransactionDaoProvider;

  private final Provider<TemplateDao> templateDaoProvider;

  private final Provider<TransactionSplitDao> transactionSplitDaoProvider;

  private final Provider<TransactionTagDao> transactionTagDaoProvider;

  public DatabaseModule_ProvideExportManagerFactory(Provider<TransactionDao> transactionDaoProvider,
      Provider<AccountDao> accountDaoProvider, Provider<CategoryDao> categoryDaoProvider,
      Provider<BudgetDao> budgetDaoProvider,
      Provider<RecurringTransactionDao> recurringTransactionDaoProvider,
      Provider<TemplateDao> templateDaoProvider,
      Provider<TransactionSplitDao> transactionSplitDaoProvider,
      Provider<TransactionTagDao> transactionTagDaoProvider) {
    this.transactionDaoProvider = transactionDaoProvider;
    this.accountDaoProvider = accountDaoProvider;
    this.categoryDaoProvider = categoryDaoProvider;
    this.budgetDaoProvider = budgetDaoProvider;
    this.recurringTransactionDaoProvider = recurringTransactionDaoProvider;
    this.templateDaoProvider = templateDaoProvider;
    this.transactionSplitDaoProvider = transactionSplitDaoProvider;
    this.transactionTagDaoProvider = transactionTagDaoProvider;
  }

  @Override
  public ExportManager get() {
    return provideExportManager(transactionDaoProvider.get(), accountDaoProvider.get(), categoryDaoProvider.get(), budgetDaoProvider.get(), recurringTransactionDaoProvider.get(), templateDaoProvider.get(), transactionSplitDaoProvider.get(), transactionTagDaoProvider.get());
  }

  public static DatabaseModule_ProvideExportManagerFactory create(
      Provider<TransactionDao> transactionDaoProvider, Provider<AccountDao> accountDaoProvider,
      Provider<CategoryDao> categoryDaoProvider, Provider<BudgetDao> budgetDaoProvider,
      Provider<RecurringTransactionDao> recurringTransactionDaoProvider,
      Provider<TemplateDao> templateDaoProvider,
      Provider<TransactionSplitDao> transactionSplitDaoProvider,
      Provider<TransactionTagDao> transactionTagDaoProvider) {
    return new DatabaseModule_ProvideExportManagerFactory(transactionDaoProvider, accountDaoProvider, categoryDaoProvider, budgetDaoProvider, recurringTransactionDaoProvider, templateDaoProvider, transactionSplitDaoProvider, transactionTagDaoProvider);
  }

  public static ExportManager provideExportManager(TransactionDao transactionDao,
      AccountDao accountDao, CategoryDao categoryDao, BudgetDao budgetDao,
      RecurringTransactionDao recurringTransactionDao, TemplateDao templateDao,
      TransactionSplitDao transactionSplitDao, TransactionTagDao transactionTagDao) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideExportManager(transactionDao, accountDao, categoryDao, budgetDao, recurringTransactionDao, templateDao, transactionSplitDao, transactionTagDao));
  }
}
