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
public final class CheckAccountTransactionsUseCase_Factory implements Factory<CheckAccountTransactionsUseCase> {
  private final Provider<TransactionRepository> repositoryProvider;

  public CheckAccountTransactionsUseCase_Factory(
      Provider<TransactionRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public CheckAccountTransactionsUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static CheckAccountTransactionsUseCase_Factory create(
      Provider<TransactionRepository> repositoryProvider) {
    return new CheckAccountTransactionsUseCase_Factory(repositoryProvider);
  }

  public static CheckAccountTransactionsUseCase newInstance(TransactionRepository repository) {
    return new CheckAccountTransactionsUseCase(repository);
  }
}
