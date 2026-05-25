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
public final class GetAccountByIdUseCase_Factory implements Factory<GetAccountByIdUseCase> {
  private final Provider<AccountRepository> repositoryProvider;

  public GetAccountByIdUseCase_Factory(Provider<AccountRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetAccountByIdUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetAccountByIdUseCase_Factory create(
      Provider<AccountRepository> repositoryProvider) {
    return new GetAccountByIdUseCase_Factory(repositoryProvider);
  }

  public static GetAccountByIdUseCase newInstance(AccountRepository repository) {
    return new GetAccountByIdUseCase(repository);
  }
}
