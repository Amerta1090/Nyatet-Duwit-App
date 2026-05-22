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
public final class GetActiveDaysCountUseCase_Factory implements Factory<GetActiveDaysCountUseCase> {
  private final Provider<TransactionRepository> repositoryProvider;

  public GetActiveDaysCountUseCase_Factory(Provider<TransactionRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetActiveDaysCountUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetActiveDaysCountUseCase_Factory create(
      Provider<TransactionRepository> repositoryProvider) {
    return new GetActiveDaysCountUseCase_Factory(repositoryProvider);
  }

  public static GetActiveDaysCountUseCase newInstance(TransactionRepository repository) {
    return new GetActiveDaysCountUseCase(repository);
  }
}
