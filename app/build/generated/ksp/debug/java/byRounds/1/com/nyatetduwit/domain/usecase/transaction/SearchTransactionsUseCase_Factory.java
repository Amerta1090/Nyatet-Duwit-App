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
public final class SearchTransactionsUseCase_Factory implements Factory<SearchTransactionsUseCase> {
  private final Provider<TransactionRepository> repositoryProvider;

  public SearchTransactionsUseCase_Factory(Provider<TransactionRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SearchTransactionsUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static SearchTransactionsUseCase_Factory create(
      Provider<TransactionRepository> repositoryProvider) {
    return new SearchTransactionsUseCase_Factory(repositoryProvider);
  }

  public static SearchTransactionsUseCase newInstance(TransactionRepository repository) {
    return new SearchTransactionsUseCase(repository);
  }
}
