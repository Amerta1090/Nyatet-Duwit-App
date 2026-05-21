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
public final class UpdateAccountUseCase_Factory implements Factory<UpdateAccountUseCase> {
  private final Provider<AccountRepository> repositoryProvider;

  public UpdateAccountUseCase_Factory(Provider<AccountRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public UpdateAccountUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static UpdateAccountUseCase_Factory create(
      Provider<AccountRepository> repositoryProvider) {
    return new UpdateAccountUseCase_Factory(repositoryProvider);
  }

  public static UpdateAccountUseCase newInstance(AccountRepository repository) {
    return new UpdateAccountUseCase(repository);
  }
}
