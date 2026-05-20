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
public final class AddAccountUseCase_Factory implements Factory<AddAccountUseCase> {
  private final Provider<AccountRepository> repositoryProvider;

  public AddAccountUseCase_Factory(Provider<AccountRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public AddAccountUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static AddAccountUseCase_Factory create(Provider<AccountRepository> repositoryProvider) {
    return new AddAccountUseCase_Factory(repositoryProvider);
  }

  public static AddAccountUseCase newInstance(AccountRepository repository) {
    return new AddAccountUseCase(repository);
  }
}
