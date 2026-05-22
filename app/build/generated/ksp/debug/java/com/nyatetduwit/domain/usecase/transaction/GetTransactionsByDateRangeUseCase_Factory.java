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
public final class GetTransactionsByDateRangeUseCase_Factory implements Factory<GetTransactionsByDateRangeUseCase> {
  private final Provider<TransactionRepository> repositoryProvider;

  public GetTransactionsByDateRangeUseCase_Factory(
      Provider<TransactionRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetTransactionsByDateRangeUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetTransactionsByDateRangeUseCase_Factory create(
      Provider<TransactionRepository> repositoryProvider) {
    return new GetTransactionsByDateRangeUseCase_Factory(repositoryProvider);
  }

  public static GetTransactionsByDateRangeUseCase newInstance(TransactionRepository repository) {
    return new GetTransactionsByDateRangeUseCase(repository);
  }
}
