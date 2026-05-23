package com.nyatetduwit.domain.usecase.transaction;

import com.nyatetduwit.domain.repository.TransactionSplitRepository;
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
public final class GetSplitsByTransactionUseCase_Factory implements Factory<GetSplitsByTransactionUseCase> {
  private final Provider<TransactionSplitRepository> repositoryProvider;

  public GetSplitsByTransactionUseCase_Factory(
      Provider<TransactionSplitRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetSplitsByTransactionUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetSplitsByTransactionUseCase_Factory create(
      Provider<TransactionSplitRepository> repositoryProvider) {
    return new GetSplitsByTransactionUseCase_Factory(repositoryProvider);
  }

  public static GetSplitsByTransactionUseCase newInstance(TransactionSplitRepository repository) {
    return new GetSplitsByTransactionUseCase(repository);
  }
}
