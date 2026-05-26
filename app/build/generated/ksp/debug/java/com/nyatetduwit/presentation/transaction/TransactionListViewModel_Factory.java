package com.nyatetduwit.presentation.transaction;

import com.nyatetduwit.domain.usecase.account.GetAccountsUseCase;
import com.nyatetduwit.domain.usecase.category.GetCategoriesUseCase;
import com.nyatetduwit.domain.usecase.template.CreateTemplateFromTransactionUseCase;
import com.nyatetduwit.domain.usecase.transaction.RestoreTransactionUseCase;
import com.nyatetduwit.domain.usecase.transaction.SearchAndFilterTransactionsUseCase;
import com.nyatetduwit.domain.usecase.transaction.SoftDeleteTransactionUseCase;
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
public final class TransactionListViewModel_Factory implements Factory<TransactionListViewModel> {
  private final Provider<SearchAndFilterTransactionsUseCase> searchAndFilterUseCaseProvider;

  private final Provider<SoftDeleteTransactionUseCase> softDeleteTransactionUseCaseProvider;

  private final Provider<RestoreTransactionUseCase> restoreTransactionUseCaseProvider;

  private final Provider<GetAccountsUseCase> getAccountsUseCaseProvider;

  private final Provider<GetCategoriesUseCase> getCategoriesUseCaseProvider;

  private final Provider<CreateTemplateFromTransactionUseCase> createTemplateFromTransactionUseCaseProvider;

  public TransactionListViewModel_Factory(
      Provider<SearchAndFilterTransactionsUseCase> searchAndFilterUseCaseProvider,
      Provider<SoftDeleteTransactionUseCase> softDeleteTransactionUseCaseProvider,
      Provider<RestoreTransactionUseCase> restoreTransactionUseCaseProvider,
      Provider<GetAccountsUseCase> getAccountsUseCaseProvider,
      Provider<GetCategoriesUseCase> getCategoriesUseCaseProvider,
      Provider<CreateTemplateFromTransactionUseCase> createTemplateFromTransactionUseCaseProvider) {
    this.searchAndFilterUseCaseProvider = searchAndFilterUseCaseProvider;
    this.softDeleteTransactionUseCaseProvider = softDeleteTransactionUseCaseProvider;
    this.restoreTransactionUseCaseProvider = restoreTransactionUseCaseProvider;
    this.getAccountsUseCaseProvider = getAccountsUseCaseProvider;
    this.getCategoriesUseCaseProvider = getCategoriesUseCaseProvider;
    this.createTemplateFromTransactionUseCaseProvider = createTemplateFromTransactionUseCaseProvider;
  }

  @Override
  public TransactionListViewModel get() {
    return newInstance(searchAndFilterUseCaseProvider.get(), softDeleteTransactionUseCaseProvider.get(), restoreTransactionUseCaseProvider.get(), getAccountsUseCaseProvider.get(), getCategoriesUseCaseProvider.get(), createTemplateFromTransactionUseCaseProvider.get());
  }

  public static TransactionListViewModel_Factory create(
      Provider<SearchAndFilterTransactionsUseCase> searchAndFilterUseCaseProvider,
      Provider<SoftDeleteTransactionUseCase> softDeleteTransactionUseCaseProvider,
      Provider<RestoreTransactionUseCase> restoreTransactionUseCaseProvider,
      Provider<GetAccountsUseCase> getAccountsUseCaseProvider,
      Provider<GetCategoriesUseCase> getCategoriesUseCaseProvider,
      Provider<CreateTemplateFromTransactionUseCase> createTemplateFromTransactionUseCaseProvider) {
    return new TransactionListViewModel_Factory(searchAndFilterUseCaseProvider, softDeleteTransactionUseCaseProvider, restoreTransactionUseCaseProvider, getAccountsUseCaseProvider, getCategoriesUseCaseProvider, createTemplateFromTransactionUseCaseProvider);
  }

  public static TransactionListViewModel newInstance(
      SearchAndFilterTransactionsUseCase searchAndFilterUseCase,
      SoftDeleteTransactionUseCase softDeleteTransactionUseCase,
      RestoreTransactionUseCase restoreTransactionUseCase, GetAccountsUseCase getAccountsUseCase,
      GetCategoriesUseCase getCategoriesUseCase,
      CreateTemplateFromTransactionUseCase createTemplateFromTransactionUseCase) {
    return new TransactionListViewModel(searchAndFilterUseCase, softDeleteTransactionUseCase, restoreTransactionUseCase, getAccountsUseCase, getCategoriesUseCase, createTemplateFromTransactionUseCase);
  }
}
