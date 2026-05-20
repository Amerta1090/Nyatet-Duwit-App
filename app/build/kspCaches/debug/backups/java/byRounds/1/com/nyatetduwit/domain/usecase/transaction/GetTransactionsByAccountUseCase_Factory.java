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
public final class GetTransactionsByAccountUseCase_Factory implements Factory<GetTransactionsByAccountUseCase> {
  private final Provider<TransactionRepository> repositoryProvider;

  public GetTransactionsByAccountUseCase_Factory(
      Provider<TransactionRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetTransactionsByAccountUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetTransactionsByAccountUseCase_Factory create(
      Provider<TransactionRepository> repositoryProvider) {
    return new GetTransactionsByAccountUseCase_Factory(repositoryProvider);
  }

  public static GetTransactionsByAccountUseCase newInstance(TransactionRepository repository) {
    return new GetTransactionsByAccountUseCase(repository);
  }
}
