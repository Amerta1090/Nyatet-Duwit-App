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
public final class GetTransactionsByTypeUseCase_Factory implements Factory<GetTransactionsByTypeUseCase> {
  private final Provider<TransactionRepository> repositoryProvider;

  public GetTransactionsByTypeUseCase_Factory(Provider<TransactionRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetTransactionsByTypeUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetTransactionsByTypeUseCase_Factory create(
      Provider<TransactionRepository> repositoryProvider) {
    return new GetTransactionsByTypeUseCase_Factory(repositoryProvider);
  }

  public static GetTransactionsByTypeUseCase newInstance(TransactionRepository repository) {
    return new GetTransactionsByTypeUseCase(repository);
  }
}
