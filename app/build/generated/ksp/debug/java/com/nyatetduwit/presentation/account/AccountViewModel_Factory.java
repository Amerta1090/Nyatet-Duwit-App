package com.nyatetduwit.presentation.account;

import com.nyatetduwit.domain.usecase.account.AddAccountUseCase;
import com.nyatetduwit.domain.usecase.account.CheckAccountTransactionsUseCase;
import com.nyatetduwit.domain.usecase.account.DeleteAccountUseCase;
import com.nyatetduwit.domain.usecase.account.GetAccountByIdUseCase;
import com.nyatetduwit.domain.usecase.account.GetAccountsUseCase;
import com.nyatetduwit.domain.usecase.account.GetTotalBalanceUseCase;
import com.nyatetduwit.domain.usecase.account.UpdateAccountUseCase;
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
public final class AccountViewModel_Factory implements Factory<AccountViewModel> {
  private final Provider<GetAccountsUseCase> getAccountsUseCaseProvider;

  private final Provider<GetAccountByIdUseCase> getAccountByIdUseCaseProvider;

  private final Provider<GetTotalBalanceUseCase> getTotalBalanceUseCaseProvider;

  private final Provider<AddAccountUseCase> addAccountUseCaseProvider;

  private final Provider<UpdateAccountUseCase> updateAccountUseCaseProvider;

  private final Provider<DeleteAccountUseCase> deleteAccountUseCaseProvider;

  private final Provider<CheckAccountTransactionsUseCase> checkAccountTransactionsUseCaseProvider;

  public AccountViewModel_Factory(Provider<GetAccountsUseCase> getAccountsUseCaseProvider,
      Provider<GetAccountByIdUseCase> getAccountByIdUseCaseProvider,
      Provider<GetTotalBalanceUseCase> getTotalBalanceUseCaseProvider,
      Provider<AddAccountUseCase> addAccountUseCaseProvider,
      Provider<UpdateAccountUseCase> updateAccountUseCaseProvider,
      Provider<DeleteAccountUseCase> deleteAccountUseCaseProvider,
      Provider<CheckAccountTransactionsUseCase> checkAccountTransactionsUseCaseProvider) {
    this.getAccountsUseCaseProvider = getAccountsUseCaseProvider;
    this.getAccountByIdUseCaseProvider = getAccountByIdUseCaseProvider;
    this.getTotalBalanceUseCaseProvider = getTotalBalanceUseCaseProvider;
    this.addAccountUseCaseProvider = addAccountUseCaseProvider;
    this.updateAccountUseCaseProvider = updateAccountUseCaseProvider;
    this.deleteAccountUseCaseProvider = deleteAccountUseCaseProvider;
    this.checkAccountTransactionsUseCaseProvider = checkAccountTransactionsUseCaseProvider;
  }

  @Override
  public AccountViewModel get() {
    return newInstance(getAccountsUseCaseProvider.get(), getAccountByIdUseCaseProvider.get(), getTotalBalanceUseCaseProvider.get(), addAccountUseCaseProvider.get(), updateAccountUseCaseProvider.get(), deleteAccountUseCaseProvider.get(), checkAccountTransactionsUseCaseProvider.get());
  }

  public static AccountViewModel_Factory create(
      Provider<GetAccountsUseCase> getAccountsUseCaseProvider,
      Provider<GetAccountByIdUseCase> getAccountByIdUseCaseProvider,
      Provider<GetTotalBalanceUseCase> getTotalBalanceUseCaseProvider,
      Provider<AddAccountUseCase> addAccountUseCaseProvider,
      Provider<UpdateAccountUseCase> updateAccountUseCaseProvider,
      Provider<DeleteAccountUseCase> deleteAccountUseCaseProvider,
      Provider<CheckAccountTransactionsUseCase> checkAccountTransactionsUseCaseProvider) {
    return new AccountViewModel_Factory(getAccountsUseCaseProvider, getAccountByIdUseCaseProvider, getTotalBalanceUseCaseProvider, addAccountUseCaseProvider, updateAccountUseCaseProvider, deleteAccountUseCaseProvider, checkAccountTransactionsUseCaseProvider);
  }

  public static AccountViewModel newInstance(GetAccountsUseCase getAccountsUseCase,
      GetAccountByIdUseCase getAccountByIdUseCase, GetTotalBalanceUseCase getTotalBalanceUseCase,
      AddAccountUseCase addAccountUseCase, UpdateAccountUseCase updateAccountUseCase,
      DeleteAccountUseCase deleteAccountUseCase,
      CheckAccountTransactionsUseCase checkAccountTransactionsUseCase) {
    return new AccountViewModel(getAccountsUseCase, getAccountByIdUseCase, getTotalBalanceUseCase, addAccountUseCase, updateAccountUseCase, deleteAccountUseCase, checkAccountTransactionsUseCase);
  }
}
