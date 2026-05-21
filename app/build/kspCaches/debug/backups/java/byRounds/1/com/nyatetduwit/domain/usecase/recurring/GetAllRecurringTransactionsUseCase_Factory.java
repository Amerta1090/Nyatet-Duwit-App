package com.nyatetduwit.domain.usecase.recurring;

import com.nyatetduwit.domain.repository.RecurringTransactionRepository;
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
public final class GetAllRecurringTransactionsUseCase_Factory implements Factory<GetAllRecurringTransactionsUseCase> {
  private final Provider<RecurringTransactionRepository> repositoryProvider;

  public GetAllRecurringTransactionsUseCase_Factory(
      Provider<RecurringTransactionRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetAllRecurringTransactionsUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetAllRecurringTransactionsUseCase_Factory create(
      Provider<RecurringTransactionRepository> repositoryProvider) {
    return new GetAllRecurringTransactionsUseCase_Factory(repositoryProvider);
  }

  public static GetAllRecurringTransactionsUseCase newInstance(
      RecurringTransactionRepository repository) {
    return new GetAllRecurringTransactionsUseCase(repository);
  }
}
