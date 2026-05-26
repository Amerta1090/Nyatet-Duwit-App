package com.nyatetduwit.presentation.transaction;

import com.nyatetduwit.domain.usecase.account.GetAccountByIdUseCase;
import com.nyatetduwit.domain.usecase.category.GetCategoryByIdUseCase;
import com.nyatetduwit.domain.usecase.transaction.BalanceUpdateService;
import com.nyatetduwit.domain.usecase.transaction.GetSplitsByTransactionUseCase;
import com.nyatetduwit.domain.usecase.transaction.GetTagsByTransactionUseCase;
import com.nyatetduwit.domain.usecase.transaction.GetTransactionByIdUseCase;
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
public final class TransactionDetailViewModel_Factory implements Factory<TransactionDetailViewModel> {
  private final Provider<GetTransactionByIdUseCase> getTransactionByIdUseCaseProvider;

  private final Provider<GetAccountByIdUseCase> getAccountByIdUseCaseProvider;

  private final Provider<GetCategoryByIdUseCase> getCategoryByIdUseCaseProvider;

  private final Provider<SoftDeleteTransactionUseCase> softDeleteTransactionUseCaseProvider;

  private final Provider<BalanceUpdateService> balanceUpdateServiceProvider;

  private final Provider<GetSplitsByTransactionUseCase> getSplitsByTransactionUseCaseProvider;

  private final Provider<GetTagsByTransactionUseCase> getTagsByTransactionUseCaseProvider;

  public TransactionDetailViewModel_Factory(
      Provider<GetTransactionByIdUseCase> getTransactionByIdUseCaseProvider,
      Provider<GetAccountByIdUseCase> getAccountByIdUseCaseProvider,
      Provider<GetCategoryByIdUseCase> getCategoryByIdUseCaseProvider,
      Provider<SoftDeleteTransactionUseCase> softDeleteTransactionUseCaseProvider,
      Provider<BalanceUpdateService> balanceUpdateServiceProvider,
      Provider<GetSplitsByTransactionUseCase> getSplitsByTransactionUseCaseProvider,
      Provider<GetTagsByTransactionUseCase> getTagsByTransactionUseCaseProvider) {
    this.getTransactionByIdUseCaseProvider = getTransactionByIdUseCaseProvider;
    this.getAccountByIdUseCaseProvider = getAccountByIdUseCaseProvider;
    this.getCategoryByIdUseCaseProvider = getCategoryByIdUseCaseProvider;
    this.softDeleteTransactionUseCaseProvider = softDeleteTransactionUseCaseProvider;
    this.balanceUpdateServiceProvider = balanceUpdateServiceProvider;
    this.getSplitsByTransactionUseCaseProvider = getSplitsByTransactionUseCaseProvider;
    this.getTagsByTransactionUseCaseProvider = getTagsByTransactionUseCaseProvider;
  }

  @Override
  public TransactionDetailViewModel get() {
    return newInstance(getTransactionByIdUseCaseProvider.get(), getAccountByIdUseCaseProvider.get(), getCategoryByIdUseCaseProvider.get(), softDeleteTransactionUseCaseProvider.get(), balanceUpdateServiceProvider.get(), getSplitsByTransactionUseCaseProvider.get(), getTagsByTransactionUseCaseProvider.get());
  }

  public static TransactionDetailViewModel_Factory create(
      Provider<GetTransactionByIdUseCase> getTransactionByIdUseCaseProvider,
      Provider<GetAccountByIdUseCase> getAccountByIdUseCaseProvider,
      Provider<GetCategoryByIdUseCase> getCategoryByIdUseCaseProvider,
      Provider<SoftDeleteTransactionUseCase> softDeleteTransactionUseCaseProvider,
      Provider<BalanceUpdateService> balanceUpdateServiceProvider,
      Provider<GetSplitsByTransactionUseCase> getSplitsByTransactionUseCaseProvider,
      Provider<GetTagsByTransactionUseCase> getTagsByTransactionUseCaseProvider) {
    return new TransactionDetailViewModel_Factory(getTransactionByIdUseCaseProvider, getAccountByIdUseCaseProvider, getCategoryByIdUseCaseProvider, softDeleteTransactionUseCaseProvider, balanceUpdateServiceProvider, getSplitsByTransactionUseCaseProvider, getTagsByTransactionUseCaseProvider);
  }

  public static TransactionDetailViewModel newInstance(
      GetTransactionByIdUseCase getTransactionByIdUseCase,
      GetAccountByIdUseCase getAccountByIdUseCase, GetCategoryByIdUseCase getCategoryByIdUseCase,
      SoftDeleteTransactionUseCase softDeleteTransactionUseCase,
      BalanceUpdateService balanceUpdateService,
      GetSplitsByTransactionUseCase getSplitsByTransactionUseCase,
      GetTagsByTransactionUseCase getTagsByTransactionUseCase) {
    return new TransactionDetailViewModel(getTransactionByIdUseCase, getAccountByIdUseCase, getCategoryByIdUseCase, softDeleteTransactionUseCase, balanceUpdateService, getSplitsByTransactionUseCase, getTagsByTransactionUseCase);
  }
}
