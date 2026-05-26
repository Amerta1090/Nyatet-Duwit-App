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
public final class GetBiggestExpenseUseCase_Factory implements Factory<GetBiggestExpenseUseCase> {
  private final Provider<TransactionRepository> repositoryProvider;

  public GetBiggestExpenseUseCase_Factory(Provider<TransactionRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetBiggestExpenseUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetBiggestExpenseUseCase_Factory create(
      Provider<TransactionRepository> repositoryProvider) {
    return new GetBiggestExpenseUseCase_Factory(repositoryProvider);
  }

  public static GetBiggestExpenseUseCase newInstance(TransactionRepository repository) {
    return new GetBiggestExpenseUseCase(repository);
  }
}
