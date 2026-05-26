package com.nyatetduwit.domain.usecase.transaction;

import com.nyatetduwit.domain.repository.TransactionRepository;
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
public final class RestoreTransactionUseCase_Factory implements Factory<RestoreTransactionUseCase> {
  private final Provider<TransactionRepository> repositoryProvider;

  public RestoreTransactionUseCase_Factory(Provider<TransactionRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public RestoreTransactionUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static RestoreTransactionUseCase_Factory create(
      Provider<TransactionRepository> repositoryProvider) {
    return new RestoreTransactionUseCase_Factory(repositoryProvider);
  }

  public static RestoreTransactionUseCase newInstance(TransactionRepository repository) {
    return new RestoreTransactionUseCase(repository);
  }
}
