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
public final class DeactivateRecurringTransactionUseCase_Factory implements Factory<DeactivateRecurringTransactionUseCase> {
  private final Provider<RecurringTransactionRepository> repositoryProvider;

  public DeactivateRecurringTransactionUseCase_Factory(
      Provider<RecurringTransactionRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public DeactivateRecurringTransactionUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static DeactivateRecurringTransactionUseCase_Factory create(
      Provider<RecurringTransactionRepository> repositoryProvider) {
    return new DeactivateRecurringTransactionUseCase_Factory(repositoryProvider);
  }

  public static DeactivateRecurringTransactionUseCase newInstance(
      RecurringTransactionRepository repository) {
    return new DeactivateRecurringTransactionUseCase(repository);
  }
}
