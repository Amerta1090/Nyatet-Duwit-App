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
public final class DeleteAccountUseCase_Factory implements Factory<DeleteAccountUseCase> {
  private final Provider<AccountRepository> repositoryProvider;

  public DeleteAccountUseCase_Factory(Provider<AccountRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public DeleteAccountUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static DeleteAccountUseCase_Factory create(
      Provider<AccountRepository> repositoryProvider) {
    return new DeleteAccountUseCase_Factory(repositoryProvider);
  }

  public static DeleteAccountUseCase newInstance(AccountRepository repository) {
    return new DeleteAccountUseCase(repository);
  }
}
