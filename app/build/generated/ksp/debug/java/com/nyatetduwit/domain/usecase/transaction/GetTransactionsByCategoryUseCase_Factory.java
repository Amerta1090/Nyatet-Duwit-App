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
public final class GetTransactionsByCategoryUseCase_Factory implements Factory<GetTransactionsByCategoryUseCase> {
  private final Provider<TransactionRepository> repositoryProvider;

  public GetTransactionsByCategoryUseCase_Factory(
      Provider<TransactionRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetTransactionsByCategoryUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetTransactionsByCategoryUseCase_Factory create(
      Provider<TransactionRepository> repositoryProvider) {
    return new GetTransactionsByCategoryUseCase_Factory(repositoryProvider);
  }

  public static GetTransactionsByCategoryUseCase newInstance(TransactionRepository repository) {
    return new GetTransactionsByCategoryUseCase(repository);
  }
}
