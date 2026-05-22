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
public final class SkipRecurringInstanceUseCase_Factory implements Factory<SkipRecurringInstanceUseCase> {
  private final Provider<RecurringTransactionRepository> repositoryProvider;

  public SkipRecurringInstanceUseCase_Factory(
      Provider<RecurringTransactionRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SkipRecurringInstanceUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static SkipRecurringInstanceUseCase_Factory create(
      Provider<RecurringTransactionRepository> repositoryProvider) {
    return new SkipRecurringInstanceUseCase_Factory(repositoryProvider);
  }

  public static SkipRecurringInstanceUseCase newInstance(
      RecurringTransactionRepository repository) {
    return new SkipRecurringInstanceUseCase(repository);
  }
}
