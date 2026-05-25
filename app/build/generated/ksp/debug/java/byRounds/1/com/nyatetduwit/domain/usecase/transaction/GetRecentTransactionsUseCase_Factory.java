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
public final class GetRecentTransactionsUseCase_Factory implements Factory<GetRecentTransactionsUseCase> {
  private final Provider<TransactionRepository> repositoryProvider;

  public GetRecentTransactionsUseCase_Factory(Provider<TransactionRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetRecentTransactionsUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetRecentTransactionsUseCase_Factory create(
      Provider<TransactionRepository> repositoryProvider) {
    return new GetRecentTransactionsUseCase_Factory(repositoryProvider);
  }

  public static GetRecentTransactionsUseCase newInstance(TransactionRepository repository) {
    return new GetRecentTransactionsUseCase(repository);
  }
}
