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
public final class FilterTransactionsUseCase_Factory implements Factory<FilterTransactionsUseCase> {
  private final Provider<TransactionRepository> repositoryProvider;

  public FilterTransactionsUseCase_Factory(Provider<TransactionRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public FilterTransactionsUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static FilterTransactionsUseCase_Factory create(
      Provider<TransactionRepository> repositoryProvider) {
    return new FilterTransactionsUseCase_Factory(repositoryProvider);
  }

  public static FilterTransactionsUseCase newInstance(TransactionRepository repository) {
    return new FilterTransactionsUseCase(repository);
  }
}
