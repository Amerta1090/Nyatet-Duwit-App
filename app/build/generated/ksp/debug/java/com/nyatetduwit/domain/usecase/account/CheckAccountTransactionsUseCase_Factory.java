package com.nyatetduwit.domain.usecase.account;

import com.nyatetduwit.domain.repository.AccountRepository;
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
public final class CheckAccountTransactionsUseCase_Factory implements Factory<CheckAccountTransactionsUseCase> {
  private final Provider<AccountRepository> repositoryProvider;

  public CheckAccountTransactionsUseCase_Factory(Provider<AccountRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public CheckAccountTransactionsUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static CheckAccountTransactionsUseCase_Factory create(
      Provider<AccountRepository> repositoryProvider) {
    return new CheckAccountTransactionsUseCase_Factory(repositoryProvider);
  }

  public static CheckAccountTransactionsUseCase newInstance(AccountRepository repository) {
    return new CheckAccountTransactionsUseCase(repository);
  }
}
