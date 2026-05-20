package com.nyatetduwit.presentation.transaction;

import com.nyatetduwit.domain.usecase.account.GetAccountsUseCase;
import com.nyatetduwit.domain.usecase.category.GetCategoriesByTypeUseCase;
import com.nyatetduwit.domain.usecase.transaction.AddTransactionUseCase;
import com.nyatetduwit.domain.usecase.transaction.BalanceUpdateService;
import com.nyatetduwit.domain.usecase.transaction.GetTransactionByIdUseCase;
import com.nyatetduwit.domain.usecase.transaction.UpdateTransactionUseCase;
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
public final class TransactionViewModel_Factory implements Factory<TransactionViewModel> {
  private final Provider<GetAccountsUseCase> getAccountsUseCaseProvider;

  private final Provider<GetCategoriesByTypeUseCase> getCategoriesByTypeUseCaseProvider;

  private final Provider<AddTransactionUseCase> addTransactionUseCaseProvider;

  private final Provider<UpdateTransactionUseCase> updateTransactionUseCaseProvider;

  private final Provider<GetTransactionByIdUseCase> getTransactionByIdUseCaseProvider;

  private final Provider<BalanceUpdateService> balanceUpdateServiceProvider;

  public TransactionViewModel_Factory(Provider<GetAccountsUseCase> getAccountsUseCaseProvider,
      Provider<GetCategoriesByTypeUseCase> getCategoriesByTypeUseCaseProvider,
      Provider<AddTransactionUseCase> addTransactionUseCaseProvider,
      Provider<UpdateTransactionUseCase> updateTransactionUseCaseProvider,
      Provider<GetTransactionByIdUseCase> getTransactionByIdUseCaseProvider,
      Provider<BalanceUpdateService> balanceUpdateServiceProvider) {
    this.getAccountsUseCaseProvider = getAccountsUseCaseProvider;
    this.getCategoriesByTypeUseCaseProvider = getCategoriesByTypeUseCaseProvider;
    this.addTransactionUseCaseProvider = addTransactionUseCaseProvider;
    this.updateTransactionUseCaseProvider = updateTransactionUseCaseProvider;
    this.getTransactionByIdUseCaseProvider = getTransactionByIdUseCaseProvider;
    this.balanceUpdateServiceProvider = balanceUpdateServiceProvider;
  }

  @Override
  public TransactionViewModel get() {
    return newInstance(getAccountsUseCaseProvider.get(), getCategoriesByTypeUseCaseProvider.get(), addTransactionUseCaseProvider.get(), updateTransactionUseCaseProvider.get(), getTransactionByIdUseCaseProvider.get(), balanceUpdateServiceProvider.get());
  }

  public static TransactionViewModel_Factory create(
      Provider<GetAccountsUseCase> getAccountsUseCaseProvider,
      Provider<GetCategoriesByTypeUseCase> getCategoriesByTypeUseCaseProvider,
      Provider<AddTransactionUseCase> addTransactionUseCaseProvider,
      Provider<UpdateTransactionUseCase> updateTransactionUseCaseProvider,
      Provider<GetTransactionByIdUseCase> getTransactionByIdUseCaseProvider,
      Provider<BalanceUpdateService> balanceUpdateServiceProvider) {
    return new TransactionViewModel_Factory(getAccountsUseCaseProvider, getCategoriesByTypeUseCaseProvider, addTransactionUseCaseProvider, updateTransactionUseCaseProvider, getTransactionByIdUseCaseProvider, balanceUpdateServiceProvider);
  }

  public static TransactionViewModel newInstance(GetAccountsUseCase getAccountsUseCase,
      GetCategoriesByTypeUseCase getCategoriesByTypeUseCase,
      AddTransactionUseCase addTransactionUseCase,
      UpdateTransactionUseCase updateTransactionUseCase,
      GetTransactionByIdUseCase getTransactionByIdUseCase,
      BalanceUpdateService balanceUpdateService) {
    return new TransactionViewModel(getAccountsUseCase, getCategoriesByTypeUseCase, addTransactionUseCase, updateTransactionUseCase, getTransactionByIdUseCase, balanceUpdateService);
  }
}
