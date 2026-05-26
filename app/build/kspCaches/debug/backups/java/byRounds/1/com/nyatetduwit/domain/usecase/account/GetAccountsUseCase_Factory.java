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
public final class GetAccountsUseCase_Factory implements Factory<GetAccountsUseCase> {
  private final Provider<AccountRepository> repositoryProvider;

  public GetAccountsUseCase_Factory(Provider<AccountRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetAccountsUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetAccountsUseCase_Factory create(Provider<AccountRepository> repositoryProvider) {
    return new GetAccountsUseCase_Factory(repositoryProvider);
  }

  public static GetAccountsUseCase newInstance(AccountRepository repository) {
    return new GetAccountsUseCase(repository);
  }
}
