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
public final class GetRecurringByIdUseCase_Factory implements Factory<GetRecurringByIdUseCase> {
  private final Provider<RecurringTransactionRepository> repositoryProvider;

  public GetRecurringByIdUseCase_Factory(
      Provider<RecurringTransactionRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetRecurringByIdUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetRecurringByIdUseCase_Factory create(
      Provider<RecurringTransactionRepository> repositoryProvider) {
    return new GetRecurringByIdUseCase_Factory(repositoryProvider);
  }

  public static GetRecurringByIdUseCase newInstance(RecurringTransactionRepository repository) {
    return new GetRecurringByIdUseCase(repository);
  }
}
